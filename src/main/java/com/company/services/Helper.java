package com.company.services;

import com.company.Exceptions.CommandLineArgumentsException;
import com.company.Exceptions.InputFileStructureException;
import com.company.Exceptions.SQLConnectorException;
import com.company.JSONModels.search.output.Search_Response;
import com.company.JSONModels.stat.input.InputDatas;
import com.company.JSONModels.stat.output.Stat_Response;
import com.company.JSONParsers.StatResponseParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;


public class Helper {

    public static void main(String[] args) throws InputFileStructureException, CommandLineArgumentsException, FileNotFoundException, SQLConnectorException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        StringBuilder response = new StringBuilder();

        InputDatas inputDatas = StatResponseParser.parse("C:/jsonchik/jsonjson.json");
        Stat_Response stat_response = new Stat_Response(inputDatas, "C:/jsonchik/jsonjsonssss.json");

        String responses = gson.toJson(stat_response);

        OutputJsonFileConstructor.createOutputJson("C:/jsonchik/jsonjsonssss.json", responses);
    }

}
