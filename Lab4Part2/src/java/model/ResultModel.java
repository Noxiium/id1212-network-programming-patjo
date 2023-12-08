package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ResultModel {
    public ArrayList<ResultTableDTO> ResultHistoryList;

    public ArrayList<ResultTableDTO> getResultHistoryList() {
        return ResultHistoryList;
    }

    public ResultModel() {
        this.ResultHistoryList = new ArrayList<ResultTableDTO>();
    }

    /**
     * Fetches results from the database and populates the ResultHistoryList
     * ARrrayList.
     *
     * @throws SQLException if there is an error executing the SQL query
     */
    public void fetchResultsFromDB() throws SQLException {
        ResultHistoryList.clear();
        Statement statement = connectToDB();
        ResultSet queryResult = statement.executeQuery("SELECT USER_ID, QUIZ_ID, SCORE FROM APP.RESULTS");

        while (queryResult.next()) {
            String userID = queryResult.getString("USER_ID");
            String quizID = queryResult.getString("QUIZ_ID");
            String score = queryResult.getString("SCORE");
            String userName = fetchUserNameFromDB(userID);
            String quizSubject = fetchQuizSubjectTextFromDB(quizID);
            ResultTableDTO result = new ResultTableDTO(userName, quizSubject, score);
            ResultHistoryList.add(result);
        }
    }

    /**
     * Retrieves the subject text of a quiz from the database based on the provided
     * quiz ID.
     *
     * @param quizID the ID of the quiz
     * @return the subject text of the quiz, or "N/A" if no quiz was found
     * @throws SQLException if an error occurs while executing the database query
     */
    private String fetchQuizSubjectTextFromDB(String quizID) throws SQLException {
        Statement statement = connectToDB();
        ResultSet queryResult = statement.executeQuery("SELECT SUBJECT FROM APP.QUIZZES WHERE ID =" + quizID);
        if (queryResult.next()) {
            String quizSubject = queryResult.getString("SUBJECT");
            return quizSubject;
        }
        return "N/A";
    }

    /**
     * Fetches the username from the database based on the given user ID.
     *
     * @param user_id the ID of the user
     * @return the username retrieved from the database, or "N/A" if no username
     *         found
     * @throws SQLException if there is an error executing the SQL query
     */
    private String fetchUserNameFromDB(String userID) throws SQLException {
        Statement statement = connectToDB();
        ResultSet queryResult = statement.executeQuery("SELECT USERNAME FROM APP.USERS WHERE ID =" + userID);
        if (queryResult.next()) {
            String userName = queryResult.getString("USERNAME");
            return userName;
        }
        return "N/A";
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
