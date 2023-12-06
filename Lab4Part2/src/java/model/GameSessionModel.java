package model;

import java.util.ArrayList;
import java.sql.*;




public class GameSessionModel{
        private String quizID;
        private int totalScore;
        public ArrayList<String> questionsID;
        public QuestionDTO currQuestionDTO;

    public GameSessionModel(){
        this.totalScore = 0;
        this.questionsID = new ArrayList<String>() ;
    }

    // {"true", null, null}
    public void checkUserAnswer(String[] userAnswer){
        for(int i = 0; i < 3; i++){
            if(!(userAnswer[i].equals(currQuestionDTO.getCorrectAnswerIndexes()[i])))
                return;
        }
        this.totalScore = this.totalScore + 2;
    }

    public void updateResultInDB(String quizID){
        
    }
    
    public void fetchQuestionsIDFromDB(String quizID) throws SQLException{
        this.quizID = quizID;
        Statement statement = connectToDB();  
        ResultSet queryResult = statement.executeQuery("SELECT QUESTION_ID FROM APP.SELECTOR WHERE QUIZ_ID = " + quizID);

        while (queryResult.next()) {
            String questionID = queryResult.getString("QUESTION_ID");
            questionsID.add(questionID);
        }
    }

    public void fetchNextQuestionsFromDB() throws SQLException{
        Statement statement = connectToDB();
        String text = "";
        String options = "";
        String answers = "";

        ResultSet queryResult = statement.executeQuery("SELECT TEXT, OPTIONS, ANSWER FROM APP.QUESTIONS WHERE ID = " + questionsID.get(0));
        while (queryResult.next()) {
            text = queryResult.getString("TEXT");
            options = queryResult.getString("OPTIONS");
            answers = queryResult.getString("ANSWER");
        }
        this.currQuestionDTO = new QuestionDTO(text, options, answers);
        questionsID.remove(0);
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