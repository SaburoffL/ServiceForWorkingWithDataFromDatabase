package com.company.JSONModels.search.output;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
public class Search_Response {
    @SerializedName("type")
    private final String TYPE = "search";

    @SerializedName("results")
    private List<Search_Result> search_results = new ArrayList<>();

    public void setSearch_results(List<Search_Result> search_results) {
        this.search_results = search_results;
    }

    public void addToSearch_results(Search_Result search_result) {
        search_results.add(search_result);
    }
}
