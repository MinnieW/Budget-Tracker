package com.budgetme.budgettracker.controllers;

import com.budgetme.budgettracker.UserPrincipal;
import com.budgetme.budgettracker.data.EventRepository;
import com.budgetme.budgettracker.data.ExpenseRepository;
import com.budgetme.budgettracker.data.UserRepository;
import com.budgetme.budgettracker.models.Event;
import com.budgetme.budgettracker.models.Expense;
import com.budgetme.budgettracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("create")
    public String createEvent(Model model){
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        return "events/create";
    }

    @PostMapping("create")
    public String processEvent(@ModelAttribute @Valid Event newEvent, Errors errors, Principal principal){
        if (errors.hasErrors()){
            return "events/create";
        }

        User currentUser = userRepository.findByName(principal.getName());
        newEvent.setUser(currentUser);
        eventRepository.save(newEvent);
        return "redirect:detail?eventId=" + newEvent.getId();
    }

    @GetMapping("home")
    public String eventHomePage(Model model, Principal principal){
        User currentUser = userRepository.findByName(principal.getName());
        List<Event> events = eventRepository.findByUserId(currentUser.getId());
        model.addAttribute("events",events);
        return "events/home";
    }

    @GetMapping("detail")
    public String viewSpecificEvent(@RequestParam Integer eventId, Model model){
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        List<Expense> expense = expenseRepository.findByEventId(eventId);
        model.addAttribute("event", event);
        model.addAttribute("expense",expense);

        BigDecimal sumPaid = new BigDecimal(0);
        for (int i=0; i < expense.size(); i++){
            sumPaid = sumPaid.add(expense.get(i).getAmount()) ;
        }
        model.addAttribute("amount",sumPaid);
        return "events/detail";
    }


}
