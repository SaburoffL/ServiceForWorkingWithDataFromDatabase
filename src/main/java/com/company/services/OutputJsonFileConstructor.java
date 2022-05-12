package com.company.services;

import com.company.Exceptions.CommandLineArgumentsException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class OutputJsonFileConstructor {
    public static void createOutputJson(String fileName, String fileContent) throws CommandLineArgumentsException {
        try {
            FileWriter outputJsonWriter = new FileWriter(fileName, true);
            outputJsonWriter.write(fileContent);
            outputJsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
