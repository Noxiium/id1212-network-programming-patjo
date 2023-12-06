package model;

import java.sql.*;


/**
 *
 * @author johansellerfredlund && patricialagerhult
 */

public class Model {
    private String userMail = "";

    public Model() {
        System.out.println("Model: Model was created");
    }

    public String getUserMail(){
        return this.userMail;
    }

    public Boolean handleLoginInPostRequest(String userMail, String userPassword) throws SQLException{
        Statement statement = connectToDB();   
        Boolean userFound = false;
        int userID =checkIfUserMailExistsInDBReturnID(userMail, statement);
        
        if(userID!=-1){
            userFound = true;
            Boolean correctPass = checkIfPasswordIsCorrect(userPassword, userID, statement);
            if(correctPass)
                return true;          
        }
        if (!userFound){
            insertUserInfoIntoDB(userMail,userPassword,statement);
            return true;
        }
            return false;
        
    }

    public int checkIfUserMailExistsInDBReturnID(String userMail, Statement statement) throws SQLException{
        Boolean userFound = false;
        statement = connectToDB();
        ResultSet queryResult = statement.executeQuery("SELECT USERNAME, ID FROM APP.USERS");
        
        while (queryResult.next()) {
            String username = queryResult.getString("USERNAME");
            int userID = queryResult.getInt("ID");

            System.out.println(username);
            if(username.equals(userMail))
                return userID;
        }
        return -1;
    }

    public void insertUserInfoIntoDB(String userMail, String userPassword, Statement statement) throws SQLException{
            statement.executeUpdate("INSERT INTO APP.USERS (USERNAME, PASSWORD) VALUES('"+ userMail +"', '" + userPassword +"')");
    
    }

    public Boolean checkIfPasswordIsCorrect(String userPassword, int userID, Statement statement) throws SQLException{
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
