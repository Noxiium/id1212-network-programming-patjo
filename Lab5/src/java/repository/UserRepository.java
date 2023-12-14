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

    public void handleUserLogin() {

    }

    /**
     * Saves a user to the database.
     *
     * @param  user The user object to be saved.
     */
    public void saveUser(User user) {
        String sql = "INSERT INTO APP.USERS (USERNAME, PASSWORD) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }

    /**
     * Fetches the user ID from the database based on the provided user's username and password.
     *
     * @param  user  the user object containing the username and password
     * @return       the user ID retrieved from the database
     */
    public Integer fetchUserID(User user) {
        String query = "SELECT ID FROM APP.USERS WHERE USERNAME = ? AND PASSWORD = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, user.getUsername(), user.getPassword());
    }

    /**
     * Retrieves the ID of a user from the database if they exist.
     *
     * @param  user  the user object containing the username
     * @return       the ID of the user if they exist, -1 otherwise
     */
    public Integer checkIfUserExistInDB(User user) {
        String query = "SELECT ID FROM APP.USERS WHERE USERNAME = ?";
        try {
            Integer userId = jdbcTemplate.queryForObject(query, Integer.class, user.getUsername());
            return userId;
        } catch (Exception e) {
            return -1; 
        }
    }

}
