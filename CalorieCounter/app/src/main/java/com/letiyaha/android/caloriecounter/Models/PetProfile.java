package com.letiyaha.android.caloriecounter.Models;

public class PetProfile {

    private String petName;
    private String petImage;

    public PetProfile() {

    }

    public PetProfile(String petName, String petImage) {
        this.petName = petName;
        this.petImage = petImage;
    }

    public String getPetName() {
        return petName;
    }

    public String getPetImage() {
        return petImage;
    }
}
