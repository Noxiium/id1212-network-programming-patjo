/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class ResultModel {
    public ArrayList<ResultTableDTO> ResultHistoryList;
    
    public ArrayList<ResultTableDTO> getResultHistoryList(){return ResultHistoryList;}
    
    public ResultModel() {
        this.ResultHistoryList = new ArrayList<ResultTableDTO>();
    }

    public void fetchResultsFromDB() throws SQLException {
        ResultHistoryList.clear();
        String userName;
        String quizSubject;
        String score;
        Statement statement = connectToDB();
        ResultSet queryResult = statement.executeQuery("SELECT USER_ID, QUIZ_ID, SCORE FROM APP.RESULTS");

        while (queryResult.next()) {
            String user_id = queryResult.getString("USER_ID");
            String quiz_id = queryResult.getString("QUIZ_ID");
            score = queryResult.getString("SCORE");
            userName = fetchUserNameFromDB(user_id);
            quizSubject = fetchQuizSubjectTextFromDB(quiz_id);
            ResultTableDTO result = new ResultTableDTO(userName, quizSubject, score);
            ResultHistoryList.add(result);
        }
    }

    private String fetchQuizSubjectTextFromDB(String quiz_id) throws SQLException {
        Statement statement = connectToDB();
        ResultSet queryResult = statement.executeQuery("SELECT SUBJECT FROM APP.QUIZZES WHERE ID =" + quiz_id);
        while (queryResult.next()) {
            String quizSubject = queryResult.getString("SUBJECT");
            return quizSubject;
        }
        return "N/A";
    }

    private String fetchUserNameFromDB(String user_id) throws SQLException {
        Statement statement = connectToDB();
        ResultSet queryResult = statement.executeQuery("SELECT USERNAME FROM APP.USERS WHERE ID =" + user_id);
        while (queryResult.next()){
            String userName = queryResult.getString("USERNAME");
            return userName;
        }
        return "N/A";
    }

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

//add sick comment