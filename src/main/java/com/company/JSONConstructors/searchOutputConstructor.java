package com.company.JSONConstructors;

import com.company.JSONModels.search.input.Criterias;
import com.company.JSONModels.search.input.Criteria;
import com.company.JSONModels.search.output.Criteria_Result;
import com.company.JSONModels.search.output.Search_Response;
import com.company.JSONModels.search.output.Search_Result;
import com.company.services.SQLConnector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class searchOutputConstructor {

    public static void serialize(Criterias criterias) {
        Search_Response searchResponse = new Search_Response();

        for (Criteria c: criterias.getCriterionList()) {
            Search_Result search_result = new Search_Result();
            search_result.setCriteria(c);
            operationHandler(search_result);
            searchResponse.addToSearch_results(search_result);
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        FileWriter outputJsonWriter = null;
        try {
            outputJsonWriter = new FileWriter("C://jsonchik/jsonskiFile.json", true);
            outputJsonWriter.write(gson.toJson(searchResponse));
            outputJsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void operationHandler(Search_Result search_result) {
        List<List<String>> SQLResult = new ArrayList<>();
        try {
            if (search_result.getCriteria().getCriteriaType()==1) {
                SQLResult = SQLConnector.getResultAsList(
                        "select firstname, lastname from public.customers where lastname ='"
                                +search_result.getCriteria().getLastName()+"'"
                );
            }
            else if (search_result.getCriteria().getCriteriaType()==2) {
                SQLResult = SQLConnector.getResultAsList(
                        "select firstname, lastname from public.customers where id in " +
                                "(" +
                                "select customers_id from public.purchases where " +
                                "products_id in (select id from products where name =" +
                                "'"+search_result.getCriteria().getProductName()+"') " +
                                "group by customers_id having(count(customers_id)>="+search_result.getCriteria().getMinTimes()+")" +
                                ")"
                );
            }
            else if (search_result.getCriteria().getCriteriaType()==3) {
                SQLResult = SQLConnector.getResultAsList(
                        "select firstname, lastname from public.customers where id in" +
                        "(select customers_id from " +
                                "public.purchases n join public.products p on p.id = n.products_id " +
                                "group by customers_id " +
                                "having(sum(price) between '"+
                                search_result.getCriteria().getMinExpenses()
                                +"'and'"+
                                search_result.getCriteria().getMaxExpenses()
                                +"'))"
                );
            }
            else if (search_result.getCriteria().getCriteriaType()==4) {
                SQLResult = SQLConnector.getResultAsList(
                    "select firstname, lastname from public.customers where id in" +
                            "(select customers_id from public.purchases group by customers_id order by count(customers_id) limit "+
                            search_result.getCriteria().getBadCustomers()+")"
                );
            }
            for (List<String> l: SQLResult) {
                Criteria_Result criteria_result = new Criteria_Result();
                if(!SQLResult.isEmpty()) {
                    criteria_result.setFirstName(l.get(0));
                    criteria_result.setLastName(l.get(1));
                }
                search_result.addToCriteria_Results(criteria_result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
