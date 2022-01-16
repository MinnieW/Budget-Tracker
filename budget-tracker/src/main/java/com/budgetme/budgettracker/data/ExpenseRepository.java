package com.budgetme.budgettracker.data;

import com.budgetme.budgettracker.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
}
