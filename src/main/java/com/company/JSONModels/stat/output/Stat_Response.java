package com.company.JSONModels.stat.output;

import com.company.Exceptions.SQLConnectorException;
import com.company.JSONModels.stat.input.InputDatas;
import com.company.services.SQLConnector;
import com.google.gson.annotations.SerializedName;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Stat_Response {
    @SerializedName("type")
    private final String TYPE = "stat";

    private String totalDays;
    private List<Customer> customers = new ArrayList<>();
    private String totalExpenses;
    private String avgExpenses;

    public Stat_Response(InputDatas inputDatas, String outputJsonFileName) throws SQLConnectorException {
        List<List<String>> CustomersList = new ArrayList<>();

        int allCustomerTotalExpenses = 0;
        double customersCount = 0;
        double avgExpenses = 0;

        try {
            CustomersList = SQLConnector.getResultAsList(
                    "select public.working_days('"+inputDatas.getStartDate()+"'," +
                            "'"+inputDatas.getEndDate()+"')"
            );
            this.totalDays = CustomersList.get(0).get(0);

            CustomersList = SQLConnector.getResultAsList(
                    "select distinct(customers_id) from purchases where purchasedate " +
                            "between '"+inputDatas.getStartDate()+"' and '"+inputDatas.getEndDate()+"'");

            if (!CustomersList.isEmpty()) {
                for (List<String> l: CustomersList) {

                    Customer customer = new Customer();
                    List<List<String>> customerInfo;
                    customerInfo = SQLConnector.getResultAsList("select firstname, lastname, p.name, sum(price) from public.customers c join purchases pur on c.id = pur.customers_id " +
                        "join public.products p on pur.products_id = p.id where purchasedate between " +
                        "'"+inputDatas.getStartDate()+"' and '"+inputDatas.getEndDate()+"' " +
                        "and c.id = "+l.get(0)+
                        " group by c.id, p.id order by c.id");
                    customer.setName(customerInfo.get(0).get(0)+" "+customerInfo.get(0).get(1));

                    int customerTotalExpenses = 0;

                    if(!customerInfo.isEmpty()) {
                        for (List<String> list: customerInfo) {
                            Purchase purchase = new Purchase();
                            purchase.setName(list.get(2));
                            purchase.setExpenses(list.get(3));
                            customer.addToPurchases(purchase);

                            customerTotalExpenses += Integer.parseInt(list.get(3));
                        }
                    }

                    customer.setTotalExpenses(Integer.toString(customerTotalExpenses));
                    allCustomerTotalExpenses += customerTotalExpenses;

                    customersCount += 1;
                    customers.add(customer);
                }
            }

        } catch (SQLException e) {
            throw new SQLConnectorException(outputJsonFileName);
        }
        this.totalExpenses = Integer.toString(allCustomerTotalExpenses);
        if (customersCount != 0) {
            avgExpenses = allCustomerTotalExpenses / customersCount;
        }
        this.avgExpenses = Double.toString(avgExpenses);
    }
}
