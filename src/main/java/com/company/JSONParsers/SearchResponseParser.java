package com.company.JSONParsers;

import com.company.Exceptions.CommandLineArgumentsException;
import com.company.JSONModels.search.input.Criterias;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class SearchResponseParser {

    public static Criterias parse(String fileName) throws CommandLineArgumentsException, FileNotFoundException {
        Criterias criterias = new Criterias();

        Gson gson = new Gson();
        FileReader inputJsonReader = new FileReader(fileName);
        criterias = gson.fromJson(inputJsonReader, Criterias.class);

        return criterias;
    }
}
