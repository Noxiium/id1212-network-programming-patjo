package controller;


import java.util.ArrayList;
import model.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.SelectSubjectService;

@Controller
@RequestMapping("/selectSubject")
public class SelectSubjectController {

    private final SelectSubjectService selectSubjectService;

    @Autowired
    public SelectSubjectController(SelectSubjectService selectSubjectService) {
        this.selectSubjectService = selectSubjectService;
    }

    /**
     * Retrieves the list of quiz subjects from the database and adds it to the model.
     *
     * @param  model  the model to which the list of quiz subjects is added
     * @return        the name of the view that should be rendered
     */
    @GetMapping
    public String showQuizSubject(Model model) {
        ArrayList<SubjectDTO> subjectList = selectSubjectService.getQuizSubjectsFromDB();
        model.addAttribute("subjectList", subjectList);

        return "selectSubjectView";

    }
}
