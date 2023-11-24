package com.example.facultdash;

public class DateValue {
    private String date;
    private boolean value;

    public DateValue() {
        // Default constructor required for Firebase
    }

    public DateValue(String date, boolean value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
