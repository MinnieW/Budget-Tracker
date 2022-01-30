package com.budgetme.budgettracker.data;

import com.budgetme.budgettracker.models.Event;
import com.budgetme.budgettracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query(value="SELECT e from Event e WHERE user_id = :userId and archive = false")
    List<Event> findByUserId(@Param("userId") Integer userId);

    @Query(value="SELECT e from Event e WHERE user_id = :userId and archive = true")
    List<Event> findByUserIdArchived(@Param("userId") Integer userId);
}
