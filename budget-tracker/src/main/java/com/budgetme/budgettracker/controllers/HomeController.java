package com.budgetme.budgettracker.controllers;

import com.budgetme.budgettracker.data.UserRepository;
import com.budgetme.budgettracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @RequestMapping("home")
    public String homePage(){
        return "home";
    }

    @RequestMapping("denied")
    public String deniedPage(Model model,Principal principal){
        User currentUser = userRepository.findByName(principal.getName());
        model.addAttribute("user",currentUser.getUsername());
        return "denied";
    }

    @GetMapping("register")
    public String registration(Model model){
        model.addAttribute("title","Registration");
        model.addAttribute(new User());

        return "register";
    }

    @PostMapping("register")
    public String processRegistration(@ModelAttribute @Valid User newUser, Errors errors){

        if(errors.hasErrors()){
            return "register";
        }
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        return "home";
    }
}
