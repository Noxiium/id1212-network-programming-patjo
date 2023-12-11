package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.UserService;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String processLoginForm(Model model) {
        
        model.addAttribute("user", new User());
        System.out.println("GET - method");
        return "loginView";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        System.out.println("POST-method");

        //userService.saveUser(user);
      
        model.addAttribute("username", user.getUsername());

        return "mainView";
    }

}
