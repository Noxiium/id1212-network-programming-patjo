package controller;

import javax.servlet.http.HttpSession;
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
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
    
        return "loginView";
    }

    /**
     * Handles user login.
     *
     * @param  user    the user object containing user information
     * @param  model   the model object for storing and retrieving data
     * @param  session the session object for managing user sessions
     * @return         the name of the main view page
     */
    @PostMapping
    public String handleUserLogin(@ModelAttribute("user") User user, Model model, HttpSession session) {
    
        userService.handleUserLogin(user);
      
        session.setAttribute("username", user.getUsername());
        session.setAttribute("userId", user.getId());
        return "mainView";
    }

}
