package com.budgetme.budgettracker.data;

import com.budgetme.budgettracker.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    //Failed to convert from type [java.lang.Object[]] to type [@org.springframework.data.jpa.repository.Query com.budgetme.budgettracker.models.Expense] for value '{Food, 12.21}'; nested exception is org.springframework.core.convert.ConverterNotFoundException:
//    @Query("SELECT e.name, e.amount from Expense e where event_id = :eventId")
//    List<Expense> findByEventId(@Param("eventId") Integer eventId);

    //works
//    @Query("SELECT new com.budgetme.budgettracker.models.Expense(e.name, e.amount) from Expense e where event_id = :eventId")
//    List<Expense> findByEventId(@Param("eventId") Integer eventId);

    @Query(value="SELECT e from Expense e WHERE event_id = :eventId")
    List<Expense> findByEventId(@Param("eventId") Integer eventId);
}
