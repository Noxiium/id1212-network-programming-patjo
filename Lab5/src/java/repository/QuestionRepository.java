/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import model.ResultDTO;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Indiana Johan
 */

@Repository
public class QuestionRepository {
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QuestionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    

    public ArrayList<QuestionDTO> getAllQuestionsFromDB(int quizID){
        List<QuestionDTO> questionList = queryQuestionsFromDB(quizID);
        return new ArrayList<>(questionList);
    }
    
    private List<QuestionDTO> queryQuestionsFromDB(int quizID) {
        String query = "SELECT Q.TEXT, Q.OPTIONS, Q.ANSWER " +
                       "FROM APP.QUESTIONS Q " +
                       "JOIN APP.SELECTOR S ON Q.ID = S.QUESTION_ID " +
                       "WHERE S.QUIZ_ID = " + quizID ;

        return jdbcTemplate.query(query, (rs, rowNum) -> new QuestionDTO(
            rs.getString("TEXT"),
            rs.getString("OPTIONS"),
            rs.getString("ANSWER")
        ));
    }
}