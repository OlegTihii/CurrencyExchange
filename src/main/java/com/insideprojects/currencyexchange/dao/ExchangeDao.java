package com.insideprojects.currencyexchange.dao;

import com.insideprojects.currencyexchange.model.Currency;
import com.insideprojects.currencyexchange.model.ExchangeRate;
import com.insideprojects.currencyexchange.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeDao {

    public List<ExchangeRate> findAll() {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        String query = """
                SELECT er.ID AS id,
                           bc.ID AS base_id,
                           bc.Code AS base_code,
                           bc.FullName AS base_name,
                           bc.Sign AS base_sign,
                           tc.ID AS target_id,
                           tc.Code AS target_code,
                           tc.FullName AS target_name,
                           tc.Sign AS target_sign,
                           er.Rate AS rate
                    FROM ExchangeRates AS er
                    JOIN Currencies AS bc ON er.BaseCurrencyId = bc.ID
                    JOIN Currencies AS tc on er.TargetCurrencyId = tc.ID
                    ORDER BY er.ID;
                    """;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                exchangeRates.add(getExchangeRate(resultSet));
            }
            return exchangeRates;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<ExchangeRate> findByCodes(String baseCurrencyCode, String targetCurrencyCode) {
        String query = """
                WITH tableBase AS (
                    SELECT ID, Code, FullName, Sign
                    FROM Currencies
                    WHERE Code = ?
                ),
                tableTarget AS (
                    SELECT ID, Code, FullName, Sign
                    FROM Currencies
                    WHERE Code = ?
                )
                SELECT er.ID AS id,
                           bc.ID AS base_id,
                           bc.Code AS base_code,
                           bc.FullName AS base_name,
                           bc.Sign AS base_sign,
                           tc.ID AS target_id,
                           tc.Code AS target_code,
                           tc.FullName AS target_name,
                           tc.Sign AS target_sign,
                           er.Rate AS rate
                FROM ExchangeRates AS er
                JOIN tableBase AS bc ON er.BaseCurrencyId = bc.ID
                JOIN tableTarget AS tc ON er.TargetCurrencyId = tc.ID;
                """;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(getExchangeRate(resultSet));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveExchangeRate(ExchangeRate exchangeRate) {
        String query = """
                INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate)
                VALUES (?, ?, ?);
                """;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, exchangeRate.getBaseCurrencyId().getId());
            preparedStatement.setInt(2, exchangeRate.getTargetCurrencyId().getId());
            preparedStatement.setBigDecimal(3, exchangeRate.getRate());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                exchangeRate.setId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate) {
        String query = """
                UPDATE ExchangeRates
                SET (BaseCurrencyId, TargetCurrencyId, Rate) = (?, ?, ?)
                WHERE ID = ?;
                """;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, exchangeRate.getBaseCurrencyId().getId());
            preparedStatement.setInt(2, exchangeRate.getTargetCurrencyId().getId());
            preparedStatement.setBigDecimal(3, exchangeRate.getRate());
            preparedStatement.setInt(4, exchangeRate.getId());
            preparedStatement.executeUpdate();

            return exchangeRate;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ExchangeRate getExchangeRate(ResultSet resultSet) throws SQLException {
        return new ExchangeRate(
                resultSet.getInt("id"),
                new Currency(
                        resultSet.getInt("base_id"),
                        resultSet.getString("base_code"),
                        resultSet.getString("base_name"),
                        resultSet.getString("base_sign")
                ),
                new Currency(
                        resultSet.getInt("target_id"),
                        resultSet.getString("target_code"),
                        resultSet.getString("target_name"),
                        resultSet.getString("target_sign")
                ),
                resultSet.getBigDecimal("rate")
        );
    }
}
