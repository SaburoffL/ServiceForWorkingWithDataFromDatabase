package com.company;

import com.company.Exceptions.CommandLineArgumentsException;
import com.company.services.InputJsonReader;
import com.company.services.OutputJsonConstructor;
import com.company.services.SQLConnector;

import java.io.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] argc) throws CommandLineArgumentsException, IOException, InterruptedException, SQLException {
        if (argc.length != 3) {throw new CommandLineArgumentsException("Incorrect number of command line arguments received");}

        String operationType = argc[0];
        String inputFileName = argc[1];
        String outputFileName = argc[2];

        String inputJsonContent = InputJsonReader.toString(argc[1]);

        OutputJsonConstructor.createOutputJson("C://jsonchik/jsonskiFile.json", InputJsonReader.toString("C://jsonchik/jsonFile.json"));


    }
}
