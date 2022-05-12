package com.company.JSONModels.ERROR;

import com.google.gson.annotations.SerializedName;

public class Error_JsonResponse {
    @SerializedName("type")
    private final String TYPE = "error";

    String message;

    public Error_JsonResponse(String message) {
        this.message = message;
    }

}
