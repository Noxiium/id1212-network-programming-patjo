package repository;

import java.util.ArrayList;
import java.util.List;
import model.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author patricialagerhult
 */
@Repository
public class SelectSubjectRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SelectSubjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<SubjectDTO> getQuizSubjectsFromDB() {
        List<SubjectDTO> subjectList = querySubjectsFromDB();
        
        return new ArrayList<>(subjectList);
    }

    private List<SubjectDTO> querySubjectsFromDB() {
        return jdbcTemplate.query(
                "SELECT ID, SUBJECT FROM APP.QUIZZES",
                (rs, rowNum) -> new SubjectDTO(rs.getString("SUBJECT"), rs.getInt("ID"))
        );
    }
}
