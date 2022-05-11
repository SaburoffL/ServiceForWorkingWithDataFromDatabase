package com.company.services;

import com.company.Exceptions.CommandLineArgumentsException;
import com.company.JSONConstructors.searchOutputConstructor;
import com.company.JSONModels.search.input.Criterias;
import com.company.JSONModels.search.input.Criteria;
import com.company.JSONModels.search.output.Search_Result;
import com.company.JSONParsers.searchParser;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.List;

public class Helper {

    public static void main(String[] args) throws SQLException, FileNotFoundException, CommandLineArgumentsException {

        Criterias criterias = searchParser.parse("C://jsonchik/jsonFile.json");
        searchOutputConstructor.serialize(criterias);


    }

}
