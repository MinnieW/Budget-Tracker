package com.budgetme.budgettracker.data;

import com.budgetme.budgettracker.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query(value="SELECT e from Expense e WHERE event_id = :eventId")
    List<Expense> findByEventId(@Param("eventId") Integer eventId);
}
