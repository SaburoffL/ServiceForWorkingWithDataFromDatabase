package com.company.services;

import com.company.Exceptions.CommandLineArgumentsException;
import com.company.Exceptions.InputFileStructureException;
import com.company.Exceptions.SQLConnectorException;
import com.company.JSONModels.search.input.Criterias;
import com.company.JSONModels.search.output.Search_Response;
import com.company.JSONModels.stat.input.InputDatas;
import com.company.JSONModels.stat.output.Stat_Response;
import com.company.JSONParsers.SearchResponseParser;
import com.company.JSONParsers.StatResponseParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;


public class Helper {

    public static void main(String[] args) throws InputFileStructureException, CommandLineArgumentsException, FileNotFoundException, SQLConnectorException {
        /*GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        StringBuilder response = new StringBuilder();

        Criterias criterias = SearchResponseParser.parse("C:/jsons/correctJsonFile.json");
        Search_Response searchResponse = new Search_Response(criterias , "C:/jsons/outputJsonFile.json");

        String responses = gson.toJson(searchResponse);

        OutputJsonFileConstructor.createOutputJson("C:/jsons/outputJsonFile.json", responses);*/

        Criterias criterias = new Criterias();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        StringBuilder response = new StringBuilder();

        String inputFileName = "C:/jsons/correctJsonFile.json";
        String outputFileName = "C://jsonchik/jsonskiFile.json";

        try {
            criterias = SearchResponseParser.parse(inputFileName);
        } catch (FileNotFoundException e) {
            String errorMessage = "Ошибка: некорректная структура входного файла";
            throw new InputFileStructureException(errorMessage, outputFileName);
        }
        Search_Response searchResponse = new Search_Response(criterias, outputFileName);
        response.append(gson.toJson(searchResponse));

        OutputJsonFileConstructor.createOutputJson(outputFileName, response.toString());

    }

}
