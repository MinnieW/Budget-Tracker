package com.budgetme.budgettracker.data;

import com.budgetme.budgettracker.models.SharedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SharedUserRepository extends JpaRepository<SharedUser, Integer> {

    @Query(value="SELECT s FROM SharedUser s WHERE user_id =  :userId and event_id = :eventId")
    SharedUser findByEventUser(@Param("userId") Integer userId, @Param("eventId") Integer eventId);
}
