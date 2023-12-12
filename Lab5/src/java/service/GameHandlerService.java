/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import model.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.ui.Model;
import repository.GameHandlerRepository;

/**
 *
 * @author Indiana Johan
 */
@Service
public class GameHandlerService {
    
    private GameHandlerRepository questionRepository;
    
    @Autowired
    public GameHandlerService(GameHandlerRepository questionRepository) {
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

    public void updateAttributeFirstQuestion(Model model,ArrayList<QuestionDTO> questionList, Integer questionIndex) {
       
            QuestionDTO currentQuestion = questionList.get(questionIndex);
            model.addAttribute("currentQuestion", currentQuestion);
            model.addAttribute("questionList", questionList);
            model.addAttribute("questionIndex", 0);
    }
}
