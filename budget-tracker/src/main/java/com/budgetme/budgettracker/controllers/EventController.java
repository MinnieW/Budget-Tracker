package com.budgetme.budgettracker.controllers;

import com.budgetme.budgettracker.data.EventRepository;
import com.budgetme.budgettracker.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
    public String eventHomePage(Model model){
        model.addAttribute("events",eventRepository.findAll());
        return "events/home";
    }

    @GetMapping("detail")
    public String viewSpecificEvent(@RequestParam Integer eventId, Model model){
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        model.addAttribute("event", event);
        return "events/detail";
    }
}
