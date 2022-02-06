package com.budgetme.budgettracker.controllers;

import com.budgetme.budgettracker.UserPrincipal;
import com.budgetme.budgettracker.data.EventRepository;
import com.budgetme.budgettracker.data.ExpenseRepository;
import com.budgetme.budgettracker.data.SharedUserRepository;
import com.budgetme.budgettracker.data.UserRepository;
import com.budgetme.budgettracker.models.*;
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

    @Autowired
    private SharedUserRepository sharedUserRepository;

    @GetMapping("home")
    public String eventHomePage(Model model, Principal principal){
        User currentUser = userRepository.findByName(principal.getName());
        List<Event> events = eventRepository.findByUserId(currentUser.getId());
        model.addAttribute("events",events);
        model.addAttribute("user",currentUser.getUsername());
        return "events/home";
    }

    @GetMapping("archive")
    public String archivePage(Model model, Principal principal){
        User currentUser = userRepository.findByName(principal.getName());
        List<Event> events = eventRepository.findByUserIdArchived(currentUser.getId());
        model.addAttribute("events",events);
        model.addAttribute("user",currentUser.getUsername());
        return "events/archive";
    }

    @GetMapping("create")
    public String createEvent(Model model, Principal principal){
        model.addAttribute("user", principal.getName());
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

    @GetMapping("edit")
    public String editEvent(@RequestParam Integer eventId, Model model, Principal principal){
        User currentUser = userRepository.findByName(principal.getName());
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();

        if (!event.getUser().equals(currentUser)){
            return "redirect:/denied";
        }
        model.addAttribute("user",currentUser.getUsername());
        model.addAttribute("event",event);
        return "events/edit";
    }

    @PostMapping("edit")
    public String processEditEvent(@ModelAttribute @Valid Event editEvent, Errors errors, @RequestParam Integer eventId){
        if (errors.hasErrors()){
            return "events/edit";
        }

        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        event.setName(editEvent.getName());
        event.setBudget(editEvent.getBudget());
        eventRepository.save(event);
        return "redirect:detail?eventId=" + event.getId();
    }

    @GetMapping("delete")
    public String deleteEvent(@RequestParam Integer eventId, Model model, Principal principal){
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        User currentUser = userRepository.findByName(principal.getName());
        if (!event.getUser().equals(currentUser)){
            return "redirect:/denied";
        }

        model.addAttribute("user",currentUser.getUsername());
        model.addAttribute("event",event);
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEvent(@RequestParam Integer eventId){
        List<Expense> expense = expenseRepository.findByEventId(eventId);
        for (int i = 0; i < expense.size(); i++){
            expenseRepository.deleteById(expense.get(i).getId());
        }
        eventRepository.deleteById(eventId);
        return "events/home";
    }

    @GetMapping("detail")
    public String viewSpecificEvent(@RequestParam Integer eventId, Model model, Principal principal){
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        User currentUser = userRepository.findByName(principal.getName());
        if (!event.getUser().equals(currentUser)){
            return "redirect:/denied";
        }

        model.addAttribute("user",currentUser.getUsername());
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

    @PostMapping("detail")
    public String archiveEvent(@RequestParam Integer eventId){
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        event.setArchive();
        eventRepository.save(event);
        if (event.getArchive()){
            return "redirect:archive";
        }
        return "redirect:detail?eventId=" + event.getId();
    }

    @GetMapping("share")
    public String shareEvent(@RequestParam Integer eventId, Model model, Principal principal){
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        User currentUser = userRepository.findByName(principal.getName());
        if (!event.getUser().equals(currentUser)) {
            return "redirect:/denied";
        }

        model.addAttribute(new SharedUser());
        model.addAttribute("event", event);
        model.addAttribute("shareType", ShareType.values());
        model.addAttribute("sharedToUser", userRepository.findAll());
        model.addAttribute("user",currentUser.getUsername());
        return "events/share";
    }

    @PostMapping("share")
    public String processShareEvent(@ModelAttribute @Valid SharedUser newSharedUser, Errors errors,@RequestParam Integer eventId,
                                    Model model, Principal principal){
        User currentUser = userRepository.findByName(principal.getName());
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        model.addAttribute("event",event);
        if (errors.hasErrors()){
            model.addAttribute("shareType", ShareType.values());
            model.addAttribute("sharedToUser", userRepository.findAll());
            model.addAttribute("user",currentUser.getUsername());
            return "events/share";
        }

        SharedUser existingEntry = sharedUserRepository.findByEventUser(newSharedUser.getUser().getId(),eventId);
        if (existingEntry != null && newSharedUser.getShareType() != existingEntry.getShareType()){
                existingEntry.setShareType(newSharedUser.getShareType());
                sharedUserRepository.save(existingEntry);
                return "redirect:detail?eventId=" + eventId;
            } else if (existingEntry != null){
                return "redirect:detail?eventId=" + eventId;
            }

        sharedUserRepository.save(newSharedUser);
        return "redirect:detail?eventId=" + eventId;
    }
}
