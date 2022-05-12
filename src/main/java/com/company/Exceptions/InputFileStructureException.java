package com.company.Exceptions;

public class InputFileStructureException extends ServiceForWorkingWithDataFromDatabaseException{
    public InputFileStructureException(String message, String outputFileName) {
        super(message);
        toJson(message, outputFileName);
    }
}
