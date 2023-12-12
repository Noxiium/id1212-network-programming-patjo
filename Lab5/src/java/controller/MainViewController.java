package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * Controller class for redirecting users to the "/mainView" path.
 * 
 */
@Controller
@RequestMapping("/mainView")
public class MainViewController {
    
    @GetMapping
     public String redirectToMainView() {
   
        return "mainView";
    }
    
    
}
