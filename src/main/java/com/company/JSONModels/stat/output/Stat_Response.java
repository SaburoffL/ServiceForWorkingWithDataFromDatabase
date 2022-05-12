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

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void setTotalExpenses(String totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public void setAvgExpenses(String avgExpenses) {
        this.avgExpenses = avgExpenses;
    }

    public Stat_Response(InputDatas inputDatas, String outputJsonFileName) throws SQLConnectorException {
        List<List<String>> SQLData = new ArrayList<>();

        try {
            SQLData = SQLConnector.getResultAsList(
                    "select public.working_days('"+inputDatas.getStartDate()+"'," +
                            "'"+inputDatas.getEndDate()+"')"
            );
            totalDays = SQLData.get(0).get(0);
        } catch (SQLException e) {
            throw new SQLConnectorException(outputJsonFileName);
        }

    }
}
