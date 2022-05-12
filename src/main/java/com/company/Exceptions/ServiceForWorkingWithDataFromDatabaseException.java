package com.company.Exceptions;

import com.company.JSONModels.ERROR.Error_JsonResponse;
import com.company.services.OutputJsonFileConstructor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServiceForWorkingWithDataFromDatabaseException extends Exception {
    public ServiceForWorkingWithDataFromDatabaseException(String message) {

    }

    public void toJson(String message, String outputFileName) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Error_JsonResponse error = new Error_JsonResponse(message);
        try {
            OutputJsonFileConstructor.createOutputJson(outputFileName, gson.toJson(error));
        }catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Не удалось преобразовать ошибку в json файл");
        }
    }
}
