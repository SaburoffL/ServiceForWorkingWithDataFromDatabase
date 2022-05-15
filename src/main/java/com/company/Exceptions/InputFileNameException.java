package com.company.Exceptions;

public class InputFileNameException extends ServiceForWorkingWithDataFromDatabaseException{
    public InputFileNameException(String message, String outputFileName) {
        super(message);
        toJson(message, outputFileName);
    }
}
