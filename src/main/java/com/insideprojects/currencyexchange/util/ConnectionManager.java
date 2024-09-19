package com.insideprojects.currencyexchange.util;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static DataSource dataSource;

    static {
        try {
            SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
            sqLiteDataSource.setUrl("jdbc:sqlite::resource:/currency_exchange.db");
            dataSource = sqLiteDataSource;
        } catch (Exception e) {
            throw new RuntimeException("Error: init DataSource", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
