package repository;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveUser(User user) {
        String sql = "INSERT INTO APP.USERS (USERNAME, PASSWORD) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }

    public void fetchUserID(User user) {
        String query = "SELECT ID FROM APP.USERS WHERE USERNAME = ? AND PASSWORD = ?";
        user.setId(jdbcTemplate.queryForObject(query, Integer.class, user.getUsername(), user.getPassword()));
    }

}
