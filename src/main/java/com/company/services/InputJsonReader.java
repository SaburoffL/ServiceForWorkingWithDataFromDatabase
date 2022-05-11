package com.company.services;

import com.company.Exceptions.CommandLineArgumentsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class InputJsonReader {
    public static String toString(String fileName) throws CommandLineArgumentsException {
        File inputJson = new File(fileName);

        if (!fileName.toLowerCase(Locale.ROOT).endsWith(".json")) {
            throw new CommandLineArgumentsException("The input file must have .json permission");
        }
        else if (!inputJson.exists()) {
            throw new CommandLineArgumentsException("File ("+fileName+") not found");
        }

        Scanner jsonScanner = null;
        StringBuilder fileContent = new StringBuilder();
        try {
            jsonScanner = new Scanner(inputJson);
            while (jsonScanner.hasNextLine()) {
                fileContent.append(jsonScanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileContent.toString();
    }
}
