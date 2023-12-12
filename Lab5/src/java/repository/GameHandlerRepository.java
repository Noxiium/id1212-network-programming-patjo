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
public class GameHandlerRepository {
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GameHandlerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public void insertResultIntoDB(int userId, int QuizId, int score){
        String query = "INSERT INTO APP.RESULTS (USER_ID, QUIZ_ID, SCORE) VALUES (?, ?,?)";
        jdbcTemplate.update(query, userId, QuizId, score);
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