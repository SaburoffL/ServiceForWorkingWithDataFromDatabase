package com.company;

import com.company.Exceptions.*;
import com.company.JSONModels.search.input.Criterias;
import com.company.JSONModels.search.output.Search_Response;
import com.company.JSONModels.stat.input.InputDatas;
import com.company.JSONModels.stat.output.Stat_Response;
import com.company.JSONParsers.SearchRequestParser;
import com.company.JSONParsers.StatRequestParser;
import com.company.services.OutputJsonFileConstructor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Locale;

public class Main {

    public static void main(String[] argc) throws CommandLineArgumentsException, InputFileStructureException, SQLConnectorException, OperationTypeException, OutputFileNameException, InputFileNameException {
        if (argc.length != 3) {
            throw new CommandLineArgumentsException("Incorrect number of command line arguments received");
        }

        String operationType = argc[0];
        String inputFileName = argc[1];
        String outputFileName = argc[2];

        File inputJsonFile = new File(inputFileName);
        File outputJsonFile = new File(outputFileName);

        if (!outputFileName.toLowerCase(Locale.ROOT).endsWith(".json")) {
            throw new OutputFileNameException("The output file must have .json permission");
        }
        else if(outputJsonFile.exists()) {
            throw new OutputFileNameException("File ("+outputFileName+") already exists");
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        StringBuilder response = new StringBuilder();

        if (!inputFileName.toLowerCase(Locale.ROOT).endsWith(".json")) {
            String errorMessage = "The input file must have .json permission";
            throw new InputFileNameException(errorMessage, outputFileName);
        }
        else if (!inputJsonFile.exists()) {
            String errorMessage = "File ("+inputFileName+") not found";
            throw new InputFileNameException(errorMessage, outputFileName);
        }

        if (operationType.equals("search")) {
            try {
                Criterias criterias = SearchRequestParser.parse(inputFileName);
                Search_Response searchResponse = new Search_Response(criterias, outputFileName);
                response.append(gson.toJson(searchResponse));
            } catch (FileNotFoundException e) {
                String errorMessage = "????????????: ???????????????????????? ?????????????????? ???????????????? ??????????";
                throw new InputFileStructureException(errorMessage, outputFileName);
            }
        }
        else if (operationType.equals("stat")) {
            try {
                InputDatas inputDatas = StatRequestParser.parse(inputFileName);
                Stat_Response stat_response = new Stat_Response(inputDatas, outputFileName);
                response.append(gson.toJson(stat_response));
            } catch (FileNotFoundException e) {
                String errorMessage = "????????????: ???????????????????????? ?????????????????? ???????????????? ??????????";
                throw new InputFileStructureException(errorMessage, outputFileName);
            }
        }
        else {
            throw new OperationTypeException("???????????????????????? ?????? ????????????????", outputFileName);
        }

        OutputJsonFileConstructor.createOutputJson(outputFileName, response.toString());
    }
}
