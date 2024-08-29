package com.insideprojects.currencyexchange.dao;

import com.insideprojects.currencyexchange.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesDAO {


    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:D:/Java Project/CurrencyExchange/src/main/resources/db/sqlite/currency_exchange.db");
        ) {
            String query = "SELECT id, Code, CurrencyName, Sign FROM Currencies";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Currency currency = new Currency();
                currency.setId(resultSet.getInt("ID"));
                currency.setCode(resultSet.getString("Code"));
                currency.setCurrencyName(resultSet.getString("CurrencyName"));
                currency.setSign(resultSet.getString("Sign"));

                currencies.add(currency);
            }
            return currencies;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency save(Currency currency) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:D:/Java Project/CurrencyExchange/src/main/resources/db/sqlite/currency_exchange.db");
        ) {
            String query = "INSERT INTO Currencies (Code, CurrencyName, Sign) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getCurrencyName());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                currency.setId(generatedKeys.getInt(1));
            }
            return currency;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Currency currency) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:D:/Java Project/CurrencyExchange/src/main/resources/db/sqlite/currency_exchange.db");
        ) {
            String query = "UPDATE Currencies (Code, CurrencyName, Sign) = (?,?,?) WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getCurrencyName());
            preparedStatement.setString(3, String.valueOf(currency.getSign()));
            preparedStatement.setInt(4, currency.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:D:/Java Project/CurrencyExchange/src/main/resources/db/sqlite/currency_exchange.db");
        ) {
            String query = "DELETE FROM Currencies WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency findByCode(String code) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:D:/Java Project/CurrencyExchange/src/main/resources/db/sqlite/currency_exchange.db");
        ) {
            String query = "SELECT id, Code, CurrencyName, Sign FROM Currencies WHERE Code = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();

            return new Currency(
                    resultSet.getInt("id"),
                    resultSet.getString("Code"),
                    resultSet.getString("CurrencyName"),
                    resultSet.getString("Sign"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
