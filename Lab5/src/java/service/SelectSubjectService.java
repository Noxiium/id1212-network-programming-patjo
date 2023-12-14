package service;

import java.util.ArrayList;
import model.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SelectSubjectRepository;

@Service
public class SelectSubjectService {
    
    private SelectSubjectRepository selectSubjectRepository;
    
    @Autowired
    public SelectSubjectService(SelectSubjectRepository selectSubjectRepository) {
        this.selectSubjectRepository = selectSubjectRepository;
    }

    
    /**
     * Retrieves a list of quiz subjects from the database.
     *
     * @return  An ArrayList of SubjectDTO objects representing the quiz subjects.
     */
    public ArrayList<SubjectDTO> getQuizSubjectsFromDB() {
         ArrayList<SubjectDTO> subjectList = selectSubjectRepository.getQuizSubjectsFromDB();
         
         return subjectList;
    }
    
}

