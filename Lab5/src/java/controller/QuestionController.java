package controller;

import java.util.ArrayList;
import model.QuestionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import service.QuestionService;

/**
 * Controller class to handle requests related to Quiz question. Fetches
 * questions and options for the chosen quiz.
 */
@Controller
@RequestMapping("/questionView")
@SessionAttributes({"questionIndex", "questionList"})
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Initialize an empty question list as a model attribute.
     *
     * @return An empty ArrayList of QuestionDTO.
     */
    @ModelAttribute("questionList")
    public ArrayList<QuestionDTO> initializeQuestionList() {
        return new ArrayList<>(); // Tom lista vid start
    }

    /**
     * Initialize the question index as a model attribute.
     *
     * @return Initial index value (0).
     */
    @ModelAttribute("questionIndex")
    public Integer initializeQuestionIndex() {
        return 0; // Index 0 vid start
    }

    /**
     * Handles GET requests to display questions.
     *
     * @param model The model used to pass data to the view
     * @param selectedSubjectId The ID of the selected subject
     * @param questionIndex The current index of the displayed question
     * @param questionList The list of questions for the chosen subject
     * @return The name of the view to be rendered
     */
    @GetMapping
    public String showQuestions(Model model,
            @RequestParam(name = "selectedSubject", required = false) Integer selectedSubjectId,
            @ModelAttribute("questionIndex") Integer questionIndex,
            @ModelAttribute("questionList") ArrayList<QuestionDTO> questionList) {

        System.out.println("GET - showQuestion");

        if (selectedSubjectId != null) {

            // Load questions from the database for the selected subject
            questionList = questionService.getAllQuestionsFromDB(selectedSubjectId);

            QuestionDTO currentQuestion = questionList.get(questionIndex);
            model.addAttribute("currentQuestion", currentQuestion);
            model.addAttribute("questionList", questionList);
            model.addAttribute("questionIndex", 0);
        } else {
            // Increase index to determine what question will be displayed next
            questionIndex++;

            // As long as there are questions left in the list, render 'questionView'
            // When all questions are answered, render 'quizCompleteView'
            if (questionIndex < questionList.size()) {
                QuestionDTO currentQuestion = questionList.get(questionIndex);
                model.addAttribute("currentQuestion", currentQuestion);
                model.addAttribute("questionList", questionList);
                model.addAttribute("questionIndex", questionIndex);
            } else {

                return "quizCompleteView";
            }
        }
        return "questionView";
    }

    /**
     * Reset the quiz and return to the main view.
     *
     * @param questionIndex The current index of the displayed question.
     * @param questionList The list of questions for the quiz.
     * @param status The session status for resetting the session attributes.
     * @return The view to be rendered, 'mainView'.
     */
    @PostMapping
    public String resetQuiz(@ModelAttribute("questionIndex") Integer questionIndex,
            @ModelAttribute("questionList") ArrayList<QuestionDTO> questionList,
            SessionStatus status) {

        questionIndex = 0;
        questionList.clear();

        status.setComplete();
        return "mainView";
    }

}
