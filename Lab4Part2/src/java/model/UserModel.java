package model;

import java.sql.*;
import java.util.ArrayList;

public class UserModel {
    private String userMail = "";

    public UserModel() {

    }

    public String getUserMail() {
        return this.userMail;
    }

    /**
     * Handles the login in a POST request.
     *
     * @param userMail     the user's email
     * @param userPassword the user's password
     * @return the user ID if the login is successful, otherwise, the newly inserted
     *         user ID
     * @throws SQLException if there is an error connecting to the database
     */
    public int handleLoginInPostRequest(String userMail, String userPassword) throws SQLException {
        Statement statement = connectToDB();
        Boolean userFound = false;
        int userID = checkIfUserMailExistsInDBReturnID(userMail, statement);

        if (userID != -1) {
            userFound = true;
            Boolean correctPass = checkIfPasswordIsCorrect(userPassword, userID, statement);
            if (correctPass)
                return userID;
        }
        if (!userFound) {
            return insertUserInfoIntoDB(userMail, userPassword, statement);
        }
        return -1;
    }

    /**
     * Check if the user's email exists in the database and return the corresponding
     * user ID.
     *
     * @param userMail  the email of the user to check
     * @param statement the statement object used to execute the SQL query
     * @return the user ID if the email exists in the database, -1 otherwise
     * @throws SQLException if there is an error executing the SQL query
     */
    private int checkIfUserMailExistsInDBReturnID(String userMail, Statement statement) throws SQLException {
        statement = connectToDB();
        ResultSet queryResult = statement.executeQuery("SELECT USERNAME, ID FROM APP.USERS");

        while (queryResult.next()) {
            String username = queryResult.getString("USERNAME");
            int userID = queryResult.getInt("ID");
            if (username.equals(userMail)) {
                return userID;
            }
        }
        return -1;
    }

    /**
     * Inserts user information into the database.
     *
     * @param userMail     the user's email address
     * @param userPassword the user's password
     * @param statement    the SQL statement object
     * @return the ID of the inserted user
     * @throws SQLException if a database error occurs
     */
    private int insertUserInfoIntoDB(String userMail, String userPassword, Statement statement) throws SQLException {
        statement.executeUpdate(
                "INSERT INTO APP.USERS (USERNAME, PASSWORD) VALUES('" + userMail + "', '" + userPassword + "')");
        ResultSet queryResult = statement.executeQuery("SELECT ID FROM APP.USERS WHERE USERNAME ='" + userMail + "'");
        if (queryResult.next()) {
            int userID = queryResult.getInt("ID");
            return userID;
        }
        return -1;
    }

    /**
     * Checks if the provided password is correct for the given user ID.
     *
     * @param userPassword the password to be checked
     * @param userID       the ID of the user
     * @param statement    the SQL statement object
     * @return true if the password is correct, false otherwise
     * @throws SQLException if an error occurs while executing the SQL query
     */
    private Boolean checkIfPasswordIsCorrect(String password, int userID, Statement statement) throws SQLException {
        ResultSet queryResult = statement.executeQuery("SELECT PASSWORD FROM APP.USERS WHERE ID = " + userID);

        if (queryResult.next()) {
            String dbPassword = queryResult.getString("PASSWORD");
            return password.equals(dbPassword);
        }
        return false;
    }

    /**
     * Connects to the database and returns a Statement object.
     *
     * @return a Statement object for executing SQL queries
     */
    private Statement connectToDB() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:derby://localhost:1527/Derby", "patjo",
                    "patjo");
            Statement statement = conn.createStatement();
            return statement;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
