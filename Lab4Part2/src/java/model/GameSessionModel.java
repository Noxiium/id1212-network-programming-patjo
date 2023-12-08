package model;

import java.util.ArrayList;
import java.sql.*;
import javax.persistence.*;

public class GameSessionModel {
    private String quizID;
    private int totalScore;
    public ArrayList<String> questionsID;
    public QuestionDTO currQuestionDTO;

    public GameSessionModel() {
        this.totalScore = 0;
        this.questionsID = new ArrayList<String>();
    }

    public int getTotalScore() {
        return this.totalScore;
    }

    /**
     * Check if the user's answer is correct and update the total score accordingly.
     *
     * @param userAnswer an array containing the user's answer
     */
    public void checkUserAnswer(String[] userAnswer) {
        for (int i = 0; i < 3; i++) {
            if (!(userAnswer[i].equals(currQuestionDTO.getCorrectAnswerIndexes()[i])))
                return;
        }
        this.totalScore = this.totalScore + 2;
    }

    /**
     * Updates the result in the database for the current GameSessionModel.
     *
     * @param userID the ID of the user
     * @throws SQLException if there is an error executing the SQL query
     */
    public void updateResultInDB(String userID) throws SQLException {

        /*------ SQL QUERY ----*/
        // Statement statement = connectToDB();
        // statement.executeUpdate("INSERT INTO APP.RESULTS (USER_ID, QUIZ_ID, SCORE)
        // VALUES("+ userID +", " + this.quizID +"," + String.valueOf(this.totalScore) +
        // ")");

        /*------- JPA --------*/
        EntityManager entityManager = Persistence.createEntityManagerFactory("Lab4Part2PU").createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            Result result = new Result();
            result.setUserId(Integer.valueOf(userID));
            result.setQuizId(Integer.valueOf(quizID));
            result.setScore(this.totalScore);

            entityManager.persist(result);
            entityManager.flush();
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();

        }
    }

    /**
     * Fetches question IDs from the database based on the given quiz ID.
     *
     * @param quizID the ID of the quiz
     * @throws SQLException if there is an error executing the SQL query
     */
    public void fetchQuestionIDsFromDB(String quizID) throws SQLException {
        this.quizID = quizID;
        Statement statement = connectToDB();
        ResultSet queryResult = statement
                .executeQuery("SELECT QUESTION_ID FROM APP.SELECTOR WHERE QUIZ_ID = " + quizID);

        while (queryResult.next()) {
            String questionID = queryResult.getString("QUESTION_ID");
            questionsID.add(questionID);
        }
    }

    /**
     * Fetches the next question from the database.
     *
     * @throws SQLException if there is an error executing the SQL query
     */
    public void fetchNextQuestionsFromDB() throws SQLException {
        Statement statement = connectToDB();
        String text = "N/A";
        String options = "N/A";
        String answers = "N/A";

        ResultSet queryResult = statement
                .executeQuery("SELECT TEXT, OPTIONS, ANSWER FROM APP.QUESTIONS WHERE ID = " + questionsID.get(0));
        while (queryResult.next()) {
            text = queryResult.getString("TEXT");
            options = queryResult.getString("OPTIONS");
            answers = queryResult.getString("ANSWER");
        }
        this.currQuestionDTO = new QuestionDTO(text, options, answers);
        questionsID.remove(0);
    }

    /**
     * Retrieves the list of quiz subjects from the database.
     *
     * @return an ArrayList of SubjectDTO objects representing the quiz subjects
     * @throws SQLException if there is an error executing the SQL query
     */
    public ArrayList<SubjectDTO> getQuizSubjectsFromDB() throws SQLException {
        ArrayList<SubjectDTO> subjectList = new ArrayList<SubjectDTO>();
        Statement statement = connectToDB();
        ResultSet queryResult = statement.executeQuery("SELECT ID, SUBJECT FROM APP.QUIZZES");

        while (queryResult.next()) {
            String subjectText = queryResult.getString("SUBJECT");
            int subjectID = queryResult.getInt("ID");

            System.out.println("GameSModel: Create subject DTO");
            SubjectDTO subject = new SubjectDTO(subjectText, subjectID);
            subjectList.add(subject);
            System.out.println("GameSModel: Subject DTO created and added");
        }
        return subjectList;
    }

    public void resetTotalScore() {
        this.totalScore = 0;
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