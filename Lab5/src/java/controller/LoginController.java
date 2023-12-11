/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Indiana Johan
 */

@Controller 
@RequestMapping ("/")

public class LoginController {
    
    @GetMapping
    public String processLoginForm(Model model) {
        System.out.println("GET - method");
        return "loginView";
    }
}
