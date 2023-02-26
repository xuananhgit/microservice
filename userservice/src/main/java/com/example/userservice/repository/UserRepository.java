package com.example.userservice.repository;
import com.example.userservice.config.sqlsource.DataSource;
import com.example.userservice.config.sqlsource.SqlGetter;
import com.example.userservice.model.User;
import jakarta.inject.Inject;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Data
public class UserRepository {
    @Inject
    private DataSource dataSource;
    @Inject
    private SqlGetter sqlGetter;

    public User findByUsername(String username) {
        String sql = sqlGetter.get("FIND_BY_USER_NAME");
        User user = null;
        try (PreparedStatement ps = dataSource.prepareStatement(sql, username); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return user;
    }

}
