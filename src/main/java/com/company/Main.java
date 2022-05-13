package com.company;

import com.company.Exceptions.CommandLineArgumentsException;
import com.company.Exceptions.InputFileStructureException;
import com.company.Exceptions.OperationTypeException;
import com.company.Exceptions.SQLConnectorException;
import com.company.JSONModels.search.input.Criterias;
import com.company.JSONModels.search.output.Search_Response;
import com.company.JSONModels.stat.input.InputDatas;
import com.company.JSONModels.stat.output.Stat_Response;
import com.company.JSONParsers.SearchResponseParser;
import com.company.JSONParsers.StatResponseParser;
import com.company.services.OutputJsonFileConstructor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Locale;

public class Main {

    public static void main(String[] argc) throws CommandLineArgumentsException, InputFileStructureException, SQLConnectorException, OperationTypeException {
        if (argc.length != 3) {throw new CommandLineArgumentsException("Incorrect number of command line arguments received",argc[2]);}

        String operationType = argc[0];
        String inputFileName = argc[1];
        String outputFileName = argc[2];

        File inputJsonFile = new File(inputFileName);
        File outputJsonFile = new File(outputFileName);

        if (!outputFileName.toLowerCase(Locale.ROOT).endsWith(".json")) {
            throw new CommandLineArgumentsException("The output file must have .json permission",outputFileName);
        }
        else if(outputJsonFile.exists()) {
            throw new CommandLineArgumentsException("File ("+outputFileName+") already exists",outputFileName);
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        StringBuilder response = new StringBuilder();

        if (!inputFileName.toLowerCase(Locale.ROOT).endsWith(".json")) {
            String errorMessage = "The input file must have .json permission";
            throw new CommandLineArgumentsException(errorMessage, outputFileName);
        }

        else if (!inputJsonFile.exists()) {
            String errorMessage = "File ("+inputFileName+") not found";
            throw new CommandLineArgumentsException(errorMessage, outputFileName);
        }

        if (operationType.equals("search")) {
            Criterias criterias = new Criterias();
            try {
                criterias = SearchResponseParser.parse(inputFileName);
            } catch (FileNotFoundException e) {
                String errorMessage = "Ошибка: некорректная структура входного файла";
                throw new CommandLineArgumentsException(errorMessage, outputFileName);
            }
            Search_Response searchResponse = new Search_Response(criterias, outputFileName);
            response.append(gson.toJson(searchResponse));
        }
        else if (operationType.equals("stat")) {
            InputDatas inputDatas = new InputDatas();
            try {
                inputDatas = StatResponseParser.parse(inputFileName);
            } catch (FileNotFoundException e) {
                String errorMessage = "Ошибка: некорректная структура входного файла";
                throw new CommandLineArgumentsException(errorMessage, outputFileName);
            }
            Stat_Response stat_response = new Stat_Response(inputDatas, outputFileName);
            response.append(gson.toJson(stat_response));
        }
        else {
            throw new OperationTypeException("Недопустимый тип операции", outputFileName);
        }

        OutputJsonFileConstructor.createOutputJson(outputFileName, response.toString());
    }
}
