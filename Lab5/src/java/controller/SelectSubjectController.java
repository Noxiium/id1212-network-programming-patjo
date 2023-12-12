package controller;


import java.util.ArrayList;
import model.SubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.SelectSubjectService;

/**
 *
 * @author patricialagerhult
 */
@Controller
@RequestMapping("/selectSubject")
public class SelectSubjectController {

    private final SelectSubjectService selectSubjectService;

    @Autowired
    public SelectSubjectController(SelectSubjectService selectSubjectService) {
        this.selectSubjectService = selectSubjectService;
    }

    @GetMapping
    public String showQuizSubject(Model model) {
        ArrayList<SubjectDTO> subjectList = selectSubjectService.getQuizSubjectsFromDB();
        model.addAttribute("subjectList", subjectList);

        return "selectSubjectView";

    }
}
