/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        ArrayList <SubjectDTO> subjectList;

        return subjectList;
       
        
    }
}
