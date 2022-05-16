package com.company.JSONModels.search.output;

import com.company.Exceptions.CommandLineArgumentsException;
import com.company.Exceptions.InputFileStructureException;
import com.company.Exceptions.SQLConnectorException;
import com.company.JSONModels.search.input.Criteria;
import com.company.JSONModels.search.input.Criterias;
import com.company.services.SQLConnector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Search_Response {
    @SerializedName("type")
    private final String TYPE = "search";

    @SerializedName("results")
    private List<Search_Result> search_results = new ArrayList<>();


    public Search_Response(Criterias criterias, String outputJsonFileName) throws InputFileStructureException, CommandLineArgumentsException, SQLConnectorException {
        if (!criterias.getCriterionList().isEmpty()) {
            for (Criteria c: criterias.getCriterionList()) {
                Search_Result search_result = new Search_Result();
                search_result.setCriteria(c);
                criterionHandler(search_result, outputJsonFileName);
                search_results.add(search_result);
            }
        }
        else {
            throw new InputFileStructureException("Передан пустой или некорректный запрос", outputJsonFileName);
        }
    }

    private static void criterionHandler(Search_Result search_result, String outputJsonFileName) throws InputFileStructureException, SQLConnectorException {
        List<List<String>> SQLResult = new ArrayList<>();
        try {
            switch (search_result.getCriteria().getCriteriaType()) {
                case(1):
                    SQLResult = SQLConnector.getResultAsList(
                            "select firstname, lastname from public.customers where lastname ='"
                                    +search_result.getCriteria().getLastName()+"'"
                    );
                    break;
                case(2):
                    SQLResult = SQLConnector.getResultAsList(
                            "select firstname, lastname from public.customers where id in " +
                                    "(" +
                                    "select customers_id from public.purchases where " +
                                    "products_id in (select id from products where name =" +
                                    "'"+search_result.getCriteria().getProductName()+"') " +
                                    "group by customers_id having(count(customers_id)>="+search_result.getCriteria().getMinTimes()+")" +
                                    ")"
                    );
                    break;
                case(3):
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
                    break;
                case(4):
                    SQLResult = SQLConnector.getResultAsList(
                            "select firstname, lastname from public.customers where id in" +
                                    "(select customers_id from public.purchases group by customers_id order by count(customers_id) limit "+
                                    search_result.getCriteria().getBadCustomers()+")"
                    );
                    break;
                default:
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    String errorMessage = "Ошибка: некорректный критерий поиска: "+
                            gson.toJson(search_result.getCriteria());
                    throw new InputFileStructureException(errorMessage, outputJsonFileName);
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
            throw new SQLConnectorException(outputJsonFileName);
        }
    }
}
