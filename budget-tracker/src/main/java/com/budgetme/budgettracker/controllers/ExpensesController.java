package com.budgetme.budgettracker.controllers;

import com.budgetme.budgettracker.data.EventRepository;
import com.budgetme.budgettracker.data.ExpenseRepository;
import com.budgetme.budgettracker.models.Event;
import com.budgetme.budgettracker.models.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("expenses")
public class ExpensesController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("create")
    public String createExpense(@RequestParam Integer eventId, Model model){
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        model.addAttribute("title", "Create Expense");
        model.addAttribute("event",event);
        model.addAttribute(new Expense());
        return "expenses/create";
    }

    @PostMapping("create")
    public String processExpense(@ModelAttribute @Valid Expense newExpense, @RequestParam Integer eventId, Errors errors){
        if (errors.hasErrors()){
            return "redirect:create";
        }

        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        newExpense.setEvent(event);
        expenseRepository.save(newExpense);
        return "events/home";
    }

    @GetMapping("edit")
    public String editExpense(@RequestParam Integer expenseId, Model model){
        Optional<Expense> result = expenseRepository.findById(expenseId);
        Expense expense = result.get();
        model.addAttribute("title", "Create Expense");
        model.addAttribute("expense",expense);
        return "expenses/edit";
    }

    @PostMapping("edit")
    public String processEditExpense(@ModelAttribute @Valid Expense editExpense, @RequestParam Integer expenseId, Errors errors){
        if (errors.hasErrors()){
            return "redirect:edit";
        }

        Optional<Expense> result = expenseRepository.findById(expenseId);
        Expense expense = result.get();
        expense.setName(editExpense.getName());
        expense.setAmount(editExpense.getAmount());
        expenseRepository.save(expense);
        return "events/home";
    }
}
