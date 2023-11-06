package com.nashtech.assetmanagement.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.String;

import java.sql.*;

public class JdbcSQLServerConnection {
    private static final Logger LOGGER = LogManager.getLogger(PropertiesFileUtil.class);
    private static Connection connectionDB = null;

    public static Connection getConnectionDB() {

        String uRL = "jdbc:sqlserver://sqlserver-team3-group1.database.windows.net;databaseName=AssetManagement";
        String username = "rookiedev";
        String password = "rookie@2022";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connectionDB = DriverManager.getConnection(uRL, username, password);
            LOGGER.info("Successful connection");

        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.info("Connection failed");

        }
        return connectionDB;
    }

    public void closeConnectionDB() throws SQLException {
        connectionDB.close();
    }

}
