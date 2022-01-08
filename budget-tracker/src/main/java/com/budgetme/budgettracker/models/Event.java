package com.budgetme.budgettracker.models;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class Event {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message="Event name is required")
    private String name;

    @Min(value=1, message = "Budget must be a number greater than 1")
    private int budget;

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

    public int getId() {
        return id;
    }
}
