package com.company.JSONParsers;

import com.company.Exceptions.CommandLineArgumentsException;
import com.company.JSONModels.search.input.Criterias;
import com.company.JSONModels.stat.input.InputDatas;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class StatResponseParser {
    public static InputDatas parse(String fileName) throws CommandLineArgumentsException, FileNotFoundException {
        InputDatas inputDatas = new InputDatas();

        Gson gson = new Gson();
        FileReader inputJsonReader = new FileReader(fileName);
        inputDatas = gson.fromJson(inputJsonReader, InputDatas.class);

        return inputDatas;
    }
}
