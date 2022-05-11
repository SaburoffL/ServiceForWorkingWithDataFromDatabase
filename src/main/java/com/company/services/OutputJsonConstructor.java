package com.company.services;

import com.company.Exceptions.CommandLineArgumentsException;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OutputJsonConstructor {
    public static void createOutputJson(String fileName, String fileContent) throws CommandLineArgumentsException {
        File outputJsonFile = new File(fileName);

        if (!fileName.toLowerCase(Locale.ROOT).endsWith(".json")) {
            throw new CommandLineArgumentsException("The output file must have .json permission");
        }

        else if(outputJsonFile.exists()) {
            throw new CommandLineArgumentsException("File ("+fileName+") already exists");
        }

        try {
            FileWriter outputJsonWriter = new FileWriter(fileName, true);
            outputJsonWriter.write(fileContent);
            outputJsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
