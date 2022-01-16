package com.budgetme.budgettracker.controllers;

import com.budgetme.budgettracker.data.ExpenseRepository;
import com.budgetme.budgettracker.models.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("expenses")
public class ExpensesController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("create")
    public String createExpense(@RequestParam Integer eventId, Model model){
        model.addAttribute("title", "Create Expense");
        model.addAttribute(new Expense());
        return "expenses/create";
    }

    @PostMapping("create")
    public String processExpense(@ModelAttribute @Valid Expense newExpense, @RequestParam Integer eventId, Errors errors){
        if (errors.hasErrors()){
            return "expenses/create?eventId=" + eventId;
        }

        expenseRepository.save(newExpense);
        return "events/home";
    }
}
