package com.letiyaha.android.caloriecounter.Models;

public class CalorieLog {

    private int breakfastCal;
    private int lunchCal;
    private int dinnerCal;
    private int snackCal;

    public CalorieLog() {

    }

    public CalorieLog(int breakfastCal, int lunchCal, int dinnerCal, int snackCal) {
        this.breakfastCal = breakfastCal;
        this.lunchCal = lunchCal;
        this.dinnerCal = dinnerCal;
        this.snackCal = snackCal;
    }

    public int getBreakfastCal() {
        return breakfastCal;
    }

    public int getLunchCal() {
        return lunchCal;
    }

    public int getDinnerCal() {
        return dinnerCal;
    }

    public int getSnackCal() {
        return snackCal;
    }

    public int sum() {
        return breakfastCal + lunchCal + dinnerCal + snackCal;
    }
}
