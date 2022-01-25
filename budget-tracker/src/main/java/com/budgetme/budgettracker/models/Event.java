package com.budgetme.budgettracker.models;



import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message="Event name is required")
    private String name;

    @Min(value=1, message = "Budget must be a number greater than 1")
    private int budget;

    @OneToMany(mappedBy = "event")
    private List<Expense> expesne = new ArrayList<>();


    @ManyToOne
    private User user;

    public Event(String name, int budget) {
        this.name = name;
        this.budget = budget;
    }

    public Event(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public List<Expense> getExpesne() {
        return expesne;
    }

//    public void setExpesne(List<Expense> expesne) {
//        this.expesne = expesne;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }
}
