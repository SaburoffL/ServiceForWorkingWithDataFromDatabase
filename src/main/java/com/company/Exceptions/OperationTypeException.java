package com.company.Exceptions;

public class OperationTypeException extends ServiceForWorkingWithDataFromDatabaseException{
    public OperationTypeException(String message, String outputFileName) {
        super(message);
        toJson(message, outputFileName);
    }
}
