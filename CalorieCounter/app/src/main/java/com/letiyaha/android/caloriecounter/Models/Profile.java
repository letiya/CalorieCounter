package com.letiyaha.android.caloriecounter.Models;

public class Profile {

    private String userName;
    private String dateOfBirth;
    private String height;
    private String weight;

    public Profile() {

    }

    public Profile(String userName, String dateOfBirth, String height, String weight) {
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
    }

    public String getUserName() {
        return userName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

}
