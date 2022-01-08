package com.budgetme.budgettracker.controllers;

import com.budgetme.budgettracker.data.UserRepository;
import com.budgetme.budgettracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String index(){
        return "index";
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

        userRepository.save(newUser);
        return "index";
    }
}
