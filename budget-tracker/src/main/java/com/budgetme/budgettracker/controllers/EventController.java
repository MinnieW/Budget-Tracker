package com.budgetme.budgettracker.controllers;

import com.budgetme.budgettracker.data.EventRepository;
import com.budgetme.budgettracker.models.Event;
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
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("create")
    public String createEvent(Model model){
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        return "events/create";
    }

    @PostMapping("create")
    public String processEvent(@ModelAttribute @Valid Event newEvent, Errors errors){
        if (errors.hasErrors()){
            return "events/create";
        }

        eventRepository.save(newEvent);
        return "events/home";
    }

    @GetMapping("home")
    public String eventHomePage(){
        return "events/home";
    }
}
