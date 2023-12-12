package service;

import java.util.ArrayList;
import model.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SelectSubjectRepository;

/**
 *
 * @author patricialagerhult
 */
@Service
public class SelectSubjectService {
    
    private SelectSubjectRepository selectSubjectRepository;
    
    @Autowired
    public SelectSubjectService(SelectSubjectRepository selectSubjectRepository) {
        this.selectSubjectRepository = selectSubjectRepository;
    }

    
    public ArrayList<SubjectDTO> getQuizSubjectsFromDB() {
         ArrayList<SubjectDTO> subjectList = selectSubjectRepository.getQuizSubjectsFromDB();
         
         return subjectList;
    }
    
}

