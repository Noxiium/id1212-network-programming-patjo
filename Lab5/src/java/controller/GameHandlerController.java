package controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import model.QuestionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.GameHandlerService;

/**
 * Controller class to handle requests related to Quiz question. Fetches
 * questions and options for the chosen quiz.
 */
@Controller
@RequestMapping("/questionView")
public class GameHandlerController {

    private final GameHandlerService questionService;

    @Autowired
    public GameHandlerController(GameHandlerService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String initializeQuiz(Model model, HttpSession session, 
            @RequestParam(name = "selectedSubject", required = false) Integer selectedSubjectId) {
        session.setAttribute("quizId", selectedSubjectId);
        questionService.initializeQuiz(selectedSubjectId);
        QuestionDTO question = questionService.getNextQuestion();
        model.addAttribute("currentQuestion", question);
        
        String userName = (String) session.getAttribute("username");
        Integer userId = (Integer) session.getAttribute("userId");
        Integer quizId = (Integer) session.getAttribute("quizId");
        System.out.println("Username: " + userName + " UserId: " + userId + " QuizId: " + quizId);


        return "questionView";
    }

    @PostMapping
    public String getNextQuestion(Model model, HttpSession session,
            @RequestParam(name = "selectedOptions0", required = false) String selectedOptions0,
            @RequestParam(name = "selectedOptions1", required = false) String selectedOptions1,
            @RequestParam(name = "selectedOptions2", required = false) String selectedOptions2) {

        List<String> selectedOptions = new ArrayList<>();
        selectedOptions.add(0, ((selectedOptions0 != null) ? selectedOptions0 : "null"));
        selectedOptions.add(1, ((selectedOptions1 != null) ? selectedOptions1 : "null"));
        selectedOptions.add(2, ((selectedOptions2 != null) ? selectedOptions2 : "null"));
        questionService.checkUserAnswer(selectedOptions);
        try {
            QuestionDTO question = questionService.getNextQuestion();
            model.addAttribute("currentQuestion", question);
            return "questionView";
        } catch (IndexOutOfBoundsException e) {
            int score = questionService.getScore();
            int userId = (int) session.getAttribute("userId");
            int quizId = (int) session.getAttribute("quizId");
            questionService.insertResultIntoDB(userId, quizId, score);
            model.addAttribute("score", score);
            return "quizCompleteView";
        }
    }
}
