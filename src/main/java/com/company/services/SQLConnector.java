package com.company.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLConnector {
    static String DataBaseURL = "jdbc:postgresql://localhost:5432/PurchasesData";
    static String DBUserName = "postgres";
    static String DBUserPassword = "akN9NjYX3s8kdM9I";

    static Statement state = null;
    static Connection con = null;

    public static void SendRequest(String SQLCommand) throws SQLException {
        try {
            con= DriverManager.getConnection(DataBaseURL, DBUserName, DBUserPassword);
            state=con.createStatement();
            state.executeQuery(SQLCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    public static List<List<String>> getResultAsList(String SQLCommand) throws SQLException {
        /**
         * Возвращает 2мерный массив [recordIndex][columnIndex]
         */
        List<List<String>> table = new ArrayList<>();
        ResultSet result;
        try {
            con= DriverManager.getConnection(DataBaseURL, DBUserName, DBUserPassword);
            state=con.createStatement();
            result=state.executeQuery(SQLCommand);
            int intForResult=0;
            while (result.next()) {
                List<String> sData = new ArrayList<>();
                for (int ColumnIndex=1; ColumnIndex <= result.getMetaData().getColumnCount(); ColumnIndex++){
                    try {
                        intForResult=result.getInt(ColumnIndex);
                        sData.add(Integer.toString(intForResult));
                    } catch (Exception e) {
                        sData.add(result.getString(ColumnIndex));
                    }
                }
                table.add(sData);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return table;
    }


}
