package com.company.Exceptions;

import com.company.services.SQLConnector;

public class SQLConnectorException extends ServiceForWorkingWithDataFromDatabaseException {

    public SQLConnectorException(String outputFileName) {
        super("Внутренняя ошибка: ошибка подключения к базе данных");
        toJson("Внутренняя ошибка: ошибка подключения к базе данных", outputFileName);
    }
}
