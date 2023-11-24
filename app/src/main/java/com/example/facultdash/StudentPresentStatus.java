package com.example.facultdash;

public class StudentPresentStatus {

    public String getUniqueID() {
        return UniqueID;
    }

    public String getName() {
        return Name;
    }

    public boolean isPresent() {
        return present;
    }

    private String UniqueID, Name;
    private boolean present;

    public StudentPresentStatus(){}


}

