package com.budgetme.budgettracker.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class SharedUser{

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name="userId")
    @NotNull(message = "Must select a user")
    private User user;

    @OneToOne
    @JoinColumn(name="eventId")
    private Event event;

    @NotNull(message = "Must select access")
    private ShareType shareType;

    public SharedUser(User user, Event event, ShareType shareType) {
        this.user = user;
        this.event = event;
        this.shareType = shareType;
    }

    public SharedUser(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ShareType getShareType() {
        return shareType;
    }

    public void setShareType(ShareType shareType) {
        this.shareType = shareType;
    }

    public int getId() {
        return id;
    }

}
