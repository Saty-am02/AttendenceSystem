package com.example.facultdash;

public class Student {
    private String Name;
    private String UniqueID;
    private int totalAbsent;
    private int totalPresent;

    boolean isPresent = false;

//    private List<DateValue> dates;
//
//    public List<DateValue> getDates() {
//        return dates;
//    }
//
//    public void setDates(List<DateValue> dates) {
//        this.dates = dates;
//        Calendar cal = Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//
//    }


    public Student() {
    }

    public void SetPresent(boolean isPresent){
        this.isPresent = isPresent;
    }

    public String getName() {
        return Name;
    }
    public String getUniqueID() {
        return UniqueID;
    }
}

