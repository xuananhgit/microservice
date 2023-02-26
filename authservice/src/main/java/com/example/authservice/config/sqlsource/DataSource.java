package com.example.authservice.config.sqlsource;

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

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        connection = getConnecting();
        return connection.prepareStatement(sql);
    }
}
