package com.company.JSONModels.search.output;

import com.company.JSONModels.search.input.Criteria;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Search_Result {
    public Criteria criteria;

    @SerializedName("results")
    private List<Criteria_Result> Criteria_Results = new ArrayList<>();

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }


    public Criteria getCriteria() {
        return this.criteria;
    }

    public void addToCriteria_Results(Criteria_Result criteria_result){
        Criteria_Results.add(criteria_result);
    }
}
