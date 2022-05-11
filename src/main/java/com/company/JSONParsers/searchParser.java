package com.company.JSONParsers;

import com.company.Exceptions.CommandLineArgumentsException;
import com.company.JSONModels.search.input.Criterias;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Locale;

public class searchParser {

    public static Criterias parse(String fileName) throws CommandLineArgumentsException {
        File inputJson = new File(fileName);

        if (!fileName.toLowerCase(Locale.ROOT).endsWith(".json")) {
            throw new CommandLineArgumentsException("The input file must have .json permission");
        }
        else if (!inputJson.exists()) {
            throw new CommandLineArgumentsException("File ("+fileName+") not found");
        }

        Criterias criterias = new Criterias();

        try {
            Gson gson = new Gson();
            FileReader inputJsonReader = new FileReader(fileName);
            criterias = gson.fromJson(inputJsonReader, Criterias.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return criterias;
    }
}
