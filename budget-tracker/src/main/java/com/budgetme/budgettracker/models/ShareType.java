package com.budgetme.budgettracker.models;

public enum ShareType {

    READ("Read"),
    READ_WRITE("Read/Write");

    private final String displayName;

    ShareType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }

}
