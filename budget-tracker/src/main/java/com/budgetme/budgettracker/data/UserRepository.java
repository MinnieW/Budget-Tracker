package com.budgetme.budgettracker.data;

import com.budgetme.budgettracker.models.Event;
import com.budgetme.budgettracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    @Query(value="SELECT u from User u WHERE username = :userName")
    User findByName(@Param("userName") String userName);

}
