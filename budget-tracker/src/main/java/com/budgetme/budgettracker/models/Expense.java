package com.budgetme.budgettracker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Expense {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Name is required")
    private String name;

    @DecimalMin(value = "0.00", message = "Amount must be greater than 0.")
    @NotNull(message = "Must enter an amount")
    private BigDecimal amount;

    @ManyToOne
    private Event event;

    public Expense(String name, BigDecimal amount) {
        this.name = name;
        this.amount = amount;
    }

    public Expense(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getId() {
        return id;
    }
}
