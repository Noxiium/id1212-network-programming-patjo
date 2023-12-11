/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import model.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.QuestionRepository;

/**
 *
 * @author Indiana Johan
 */
@Service
public class QuestionService {
    
    private QuestionRepository questionRepository;
    
    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
    * Retrieves all the results from the database.
    *
    * @return the list of all results from the database
    */
    public ArrayList<QuestionDTO> getAllQuestionsFromDB(int quizID){
        ArrayList<QuestionDTO> questionList = questionRepository.getAllQuestionsFromDB(quizID);
        return questionList;
    }
}
