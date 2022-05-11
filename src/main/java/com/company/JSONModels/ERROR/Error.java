package com.company.JSONModels.ERROR;

import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("type")
    private final String TYPE = "error";

    String message;

    public Error(String message) {
        this.message = message;
    }
}
