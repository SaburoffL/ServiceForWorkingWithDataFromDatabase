package com.company.JSONParsers;

import com.company.JSONModels.search.input.Criterias;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class SearchRequestParser {

    public static Criterias parse(String fileName) throws FileNotFoundException {
        Criterias criterias = new Criterias();

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setLenient().create();
        FileReader inputJsonReader = new FileReader(fileName);
        criterias = gson.fromJson(inputJsonReader, Criterias.class);

        return criterias;
    }
}
