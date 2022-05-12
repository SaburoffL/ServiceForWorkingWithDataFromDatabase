package com.company.Exceptions;

public class CommandLineArgumentsException extends ServiceForWorkingWithDataFromDatabaseException{
    public CommandLineArgumentsException(String message, String outputFileName) {
        super(message);
        toJson(message, outputFileName);
    }
}
