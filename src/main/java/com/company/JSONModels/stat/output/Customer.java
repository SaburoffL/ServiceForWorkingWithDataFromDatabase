package com.company.JSONModels.stat.output;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private List<Purchase> purchases = new ArrayList();
    private String totalExpenses;

    public void setName(String name) {
        this.name = name;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void setTotalExpenses(String totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}
