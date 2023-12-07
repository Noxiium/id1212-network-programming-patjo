package model;

import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author johansellerfredlund && patricialagerhult
 */

public class UserModel {
    private String userMail = "";

    public UserModel() {
        
    }

    public String getUserMail(){
        return this.userMail;
    }

    public ArrayList<SubjectDTO> getQuizSubjectFromDB() throws SQLException{
        ArrayList<SubjectDTO> subjectList = new ArrayList<SubjectDTO>();
        Statement statement = connectToDB();  
        ResultSet queryResult = statement.executeQuery("SELECT ID, SUBJECT FROM APP.QUIZZES");
        
        while (queryResult.next()) {
            String subjectText = queryResult.getString("SUBJECT");
            int subjectID = queryResult.getInt("ID");
            SubjectDTO subject = new SubjectDTO(subjectText, subjectID);
            subjectList.add(subject);
        }
        return subjectList;
    }

    public int handleLoginInPostRequest(String userMail, String userPassword) throws SQLException{
        Statement statement = connectToDB();   
        Boolean userFound = false;
        int userID =checkIfUserMailExistsInDBReturnID(userMail, statement);
        
        if(userID!=-1){
            userFound = true;
            Boolean correctPass = checkIfPasswordIsCorrect(userPassword, userID, statement);
            if(correctPass)
                return userID;
;          
        }
        if (!userFound){
            return insertUserInfoIntoDB(userMail,userPassword,statement);
            
        }
            return -1;
        
    }

    private int checkIfUserMailExistsInDBReturnID(String userMail, Statement statement) throws SQLException{
        Boolean userFound = false;
        statement = connectToDB();
        ResultSet queryResult = statement.executeQuery("SELECT USERNAME, ID FROM APP.USERS");
        
        while (queryResult.next()) {
            String username = queryResult.getString("USERNAME");
            int userID = queryResult.getInt("ID");
            if(username.equals(userMail))
                return userID;
        }
        return -1;
    }

    private int insertUserInfoIntoDB(String userMail, String userPassword, Statement statement) throws SQLException{
        statement.executeUpdate("INSERT INTO APP.USERS (USERNAME, PASSWORD) VALUES('"+ userMail +"', '" + userPassword +"')");
        ResultSet queryResult = statement.executeQuery("SELECT ID FROM APP.USERS WHERE USERNAME ='" + userMail + "'");
        while (queryResult.next()){
            int userID = queryResult.getInt("ID");
            return userID;
        }
        return -1;
    }

    private Boolean checkIfPasswordIsCorrect(String userPassword, int userID, Statement statement) throws SQLException{
        ResultSet queryResult = statement.executeQuery("SELECT PASSWORD FROM APP.USERS WHERE ID = " + userID);
        
        if (queryResult.next()) {
            String dbPassword = queryResult.getString("PASSWORD");
            return userPassword.equals(dbPassword);
        }
        return false;
    }

    private Statement connectToDB(){
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:derby://localhost:1527/Derby", "patjo", "patjo");      
            Statement statement = conn.createStatement(); 

            return statement;
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return null;
    }
}
