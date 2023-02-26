package com.example.userservice.config.sqlsource;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Objects;
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "datasource")
public class DataSource {
    private Connection connection;
    private String url;
    private String username;
    private String password;
    private String postgres;

    public Connection getConnecting() {
        try {
            if (Objects.isNull(connection)) {
                Class.forName(postgres);
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected");
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return connection;
    }

    public PreparedStatement prepareStatement(String sql, Object... idx) throws SQLException {
        connection = getConnecting();
        PreparedStatement preparedStatement;
        if (Objects.isNull(idx)) {
            log.info(sql);
            return connection.prepareStatement(sql);
        }
        preparedStatement = connection.prepareStatement(sql);
        int index = 0;
        for (Object id : idx) {
            if (id instanceof Integer) {
                preparedStatement.setInt(++index, (Integer) id);
            }
            if (id instanceof String) {
                preparedStatement.setString(++index, (String) id);
            }
            if (id instanceof Date) {
                preparedStatement.setDate(++index, (Date) id);
            }
        }
        log.info(preparedStatement.toString());
        return preparedStatement;
    }
}
