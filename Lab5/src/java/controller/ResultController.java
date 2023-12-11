
package controller;

import java.util.ArrayList;
import model.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.ResultService;


@Controller
@RequestMapping("/result")
public class ResultController {

    private final ResultService resultService;
    
    @Autowired
    public ResultController(ResultService resultService){
        this.resultService = resultService;
    }
    
    /**
     * Retrieves and displays past results.
     *
     * @param  model	the model used to pass data to the view
     * @return         	the name of the view to be rendered
     */
    @GetMapping("/result")
    public String showPastResults(Model model) {
        ArrayList<ResultDTO> resultList = resultService.getAllResultsFromDB();
        model.addAttribute("resultList", resultList);
        return "pastResultsView";
    }
}



