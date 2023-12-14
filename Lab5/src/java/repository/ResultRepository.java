package repository;

import model.ResultDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ResultRepository {
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResultRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * Retrieves all the results from the database.
     *
     * @return  an ArrayList containing all the results from the database
     */
    public ArrayList<ResultDTO> getAllResultsFromDB(){
        List<ResultDTO> resultList = queryResultsFromDB();
        return new ArrayList<>(resultList);
    }
    
/**
 * Retrieves the results from the database.
 *
 * @return a list of ResultDTO objects containing the username, subject, and score
 */
    private List<ResultDTO> queryResultsFromDB() {
        String query = "SELECT U.USERNAME, Q.SUBJECT, R.SCORE " +
                       "FROM APP.RESULTS R " +
                       "INNER JOIN APP.USERS U ON R.USER_ID = U.ID " +
                       "INNER JOIN APP.QUIZZES Q ON R.QUIZ_ID = Q.ID ";

        return jdbcTemplate.query(query, (rs, rowNum) -> new ResultDTO(
            rs.getString("USERNAME"),
            rs.getString("SUBJECT"),
            rs.getInt("SCORE")
        ));
    }
}
