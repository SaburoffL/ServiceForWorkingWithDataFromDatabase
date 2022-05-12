package com.company;

import com.company.Exceptions.CommandLineArgumentsException;
import com.company.Exceptions.InputFileStructureException;
import com.company.JSONModels.ERROR.Error_JsonResponse;
import com.company.JSONModels.search.input.Criterias;
import com.company.JSONModels.search.output.Search_Response;
import com.company.JSONParsers.searchParser;
import com.company.services.OutputJsonFileConstructor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.sql.SQLException;
import java.util.Locale;

public class Main {

    public static void main(String[] argc) throws CommandLineArgumentsException, InputFileStructureException {
        if (argc.length != 3) {throw new CommandLineArgumentsException("Incorrect number of command line arguments received");}

        String operationType = argc[0];
        String inputFileName = argc[1];
        String outputFileName = argc[2];

        File inputJson = new File(inputFileName);
        File outputJsonFile = new File(outputFileName);

        if (!outputFileName.toLowerCase(Locale.ROOT).endsWith(".json")) {
            throw new CommandLineArgumentsException("The output file must have .json permission");
        }
        else if(outputJsonFile.exists()) {
            throw new CommandLineArgumentsException("File ("+outputFileName+") already exists");
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        StringBuilder response = new StringBuilder();

        if (!inputFileName.toLowerCase(Locale.ROOT).endsWith(".json")) {
            String errorMessage = "The input file must have .json permission";
            Error_JsonResponse error = new Error_JsonResponse(errorMessage);
            response.append(gson.toJson(error));
            OutputJsonFileConstructor.createOutputJson(outputFileName, response.toString());
            throw new CommandLineArgumentsException(errorMessage);
        }

        else if (!inputJson.exists()) {
            String errorMessage = "File ("+inputFileName+") not found";
            Error_JsonResponse error = new Error_JsonResponse(errorMessage);
            response.append(gson.toJson(error));
            OutputJsonFileConstructor.createOutputJson(outputFileName, response.toString());
            throw new CommandLineArgumentsException(errorMessage);
        }

        if (operationType.equals("search")) {
            Criterias criterias = null;
            try {
                criterias = searchParser.parse(inputFileName);
            } catch (FileNotFoundException e) {
                String errorMessage = "Ошибка: некорректная структура входного файла";
                Error_JsonResponse error = new Error_JsonResponse(errorMessage);
                response.append(gson.toJson(error));
                OutputJsonFileConstructor.createOutputJson(outputFileName, response.toString());
                throw new CommandLineArgumentsException(errorMessage);
            }
            Search_Response searchResponse = new Search_Response(criterias, outputFileName);
            response.append(gson.toJson(searchResponse));
        }

        OutputJsonFileConstructor.createOutputJson(outputFileName, response.toString());
    }
}
