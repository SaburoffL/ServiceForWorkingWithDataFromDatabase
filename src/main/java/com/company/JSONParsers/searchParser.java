package com.company.JSONParsers;

import com.company.Exceptions.CommandLineArgumentsException;
import com.company.JSONModels.ERROR.Error_JsonResponse;
import com.company.JSONModels.search.input.Criterias;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Locale;

public class searchParser {

    public static Criterias parse(String fileName) throws CommandLineArgumentsException, FileNotFoundException {
        Criterias criterias = new Criterias();

        Gson gson = new Gson();
        FileReader inputJsonReader = new FileReader(fileName);
        criterias = gson.fromJson(inputJsonReader, Criterias.class);

        return criterias;
    }
}
