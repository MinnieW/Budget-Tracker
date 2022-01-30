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

    private Boolean archive = false;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getArchive() {
        return archive;
    }

//    public void setArchive(Boolean archive) {
//        this.archive = archive;
//    }

    public void setArchive() {
        this.archive = !this.archive;
    }

    public int getId() {
        return id;
    }
}
