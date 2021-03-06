package com.budgetme.budgettracker.controllers;

import com.budgetme.budgettracker.data.EventRepository;
import com.budgetme.budgettracker.data.ExpenseRepository;
import com.budgetme.budgettracker.data.SharedUserRepository;
import com.budgetme.budgettracker.data.UserRepository;
import com.budgetme.budgettracker.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("expenses")
public class ExpensesController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SharedUserRepository sharedUserRepository;

    @GetMapping("create")
    public String createExpense(@RequestParam Integer eventId, Model model, Principal principal){
        User currentUser = userRepository.findByName(principal.getName());
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        SharedUser possibleShare = sharedUserRepository.findByEventUser(currentUser.getId(), event.getId());

        if (!event.getUser().equals(currentUser) && (possibleShare == null || possibleShare.getShareType() == ShareType.READ)){
            return "redirect:/denied";
        }

        model.addAttribute("user",currentUser.getUsername());
        model.addAttribute("event", event);
        model.addAttribute(new Expense());
        return "expenses/create";
    }

    @PostMapping("create")
    public String processExpense(@ModelAttribute @Valid Expense newExpense, Errors errors, @RequestParam Integer eventId, Model model){
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();

        if (errors.hasErrors()){
            model.addAttribute("event",event);
            return "expenses/create";
        }

        newExpense.setEvent(event);
        expenseRepository.save(newExpense);
        return "redirect:/events/detail?eventId=" + event.getId();
    }

    @GetMapping("edit")
    public String editExpense(@RequestParam Integer expenseId, Model model, Principal principal){
        User currentUser = userRepository.findByName(principal.getName());
        Optional<Expense> result = expenseRepository.findById(expenseId);
        Expense expense = result.get();
        SharedUser possibleShare = sharedUserRepository.findByEventUser(currentUser.getId(), expense.getEvent().getId());

        if (!expense.getEvent().getUser().equals(currentUser) && (possibleShare == null || possibleShare.getShareType() == ShareType.READ)){
            return "redirect:/denied";
        }

        model.addAttribute("user",currentUser.getUsername());
        model.addAttribute("title", "Create Expense");
        model.addAttribute("expense",expense);
        return "expenses/edit";
    }

    @PostMapping("edit")
    public String processEditExpense(@ModelAttribute @Valid Expense editExpense, Errors errors, @RequestParam Integer expenseId){
        if (errors.hasErrors()){
            return "expenses/edit";
        }

        Optional<Expense> result = expenseRepository.findById(expenseId);
        Expense expense = result.get();
        expense.setName(editExpense.getName());
        expense.setAmount(editExpense.getAmount());
        expenseRepository.save(expense);
        return "redirect:/events/detail?eventId=" + expense.getEvent().getId();
    }

    @GetMapping("delete")
    public String deleteExpense(@RequestParam Integer expenseId, Model model, Principal principal){
        User currentUser = userRepository.findByName(principal.getName());
        Optional<Expense> result = expenseRepository.findById(expenseId);
        Expense expense = result.get();
        SharedUser possibleShare = sharedUserRepository.findByEventUser(currentUser.getId(), expense.getEvent().getId());

        if (!expense.getEvent().getUser().equals(currentUser) && (possibleShare == null || possibleShare.getShareType() == ShareType.READ)){
            return "redirect:/denied";
        }

        model.addAttribute("user",currentUser.getUsername());
        model.addAttribute("expense",expense);
        return "expenses/delete";
    }

    @PostMapping("delete")
    public String processDeleteExpense(@RequestParam Integer expenseId){
        Optional<Expense> result = expenseRepository.findById(expenseId);
        Expense expense = result.get();
        expenseRepository.deleteById(expenseId);
        return "redirect:/events/detail?eventId=" + expense.getEvent().getId();
    }
}
