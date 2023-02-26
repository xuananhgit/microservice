package com.example.authservice.repository;
import com.example.authservice.config.sqlsource.DataSource;
import com.example.authservice.config.sqlsource.SqlGetter;
import com.example.authservice.model.Role;
import com.example.authservice.model.User;
import jakarta.inject.Inject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@Data
@Slf4j
public class UserRepository {
    @Inject
    private DataSource dataSource;
    @Inject
    private SqlGetter sqlGetter;

    public Optional<User> findByEmail(String email) {
        String sql = sqlGetter.get("FIND_BY_EMAIL");
        ResultSet rs = null;
        User user = null;
        try (PreparedStatement ps = dataSource.prepareStatement(sql)) {
            ps.setString(1, email);
            rs = ps.executeQuery();
            log.info(ps.toString());
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("EMAIL"));
                user.setFirstname(rs.getString("LASTNAME"));
                user.setFirstname(rs.getString("FIRSTNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setRole(Role.valueOf(rs.getString("ROLE")));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                assert rs != null;
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return Optional.ofNullable(user);
    }

    public boolean save(User user) {
        int maxId = getCountIdUser();
        String sql = sqlGetter.get("INSERT_USER");
        try(PreparedStatement ps = dataSource.prepareStatement(sql)) {
            int idx = 0;
            ps.setInt(++idx, ++maxId);
            ps.setString(++idx, user.getEmail());
            ps.setString(++idx, user.getFirstname());
            ps.setString(++idx, user.getLastname());
            ps.setString(++idx, user.getPassword());
            ps.setString(++idx, user.getRole().name());
            log.info(ps.toString());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private int getCountIdUser() {
        String sql = sqlGetter.get("GET_MAX_ID");
        try (PreparedStatement ps = dataSource.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()){
                return rs.getInt("MAX_ID");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return 0;
    }
}
