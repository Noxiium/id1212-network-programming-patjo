/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.QuestionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.QuestionService;

@Controller
@RequestMapping("/questionView")
public class QuestionController {
    
    private final QuestionService questionService;
    
    @Autowired
    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }
    
    /**
     * Retrieves and displays past results.
     *
     * @param  model	the model used to pass data to the view
     * @return         	the name of the view to be rendered
     */
    @GetMapping("/questionView")
    public String showPastResults(Model model,
                                  @RequestParam(name = "selectedSubject", required = false) Integer selectedSubjectId){
        
        if(selectedSubjectId != null){
            ArrayList<QuestionDTO> questionList = questionService.getAllQuestionsFromDB(selectedSubjectId);
            for(QuestionDTO q : questionList){
                System.out.println(q);
            }
            model.addAttribute("questionList", questionList);
        }
        return "questionView";
    }
}
