package com.letiyaha.android.caloriecounter.Models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {

    private static final String PROFILE = "Profile";
    private static final String PETPROFILE = "PetProfile";
    private static final String FOOD = "Food";
    private static final String BREAKFAST = "Breakfast";
    private static final String LUNCH = "Lunch";
    private static final String DINNER = "Dinner";
    private static final String SNACK = "Snack";
    private static final String FOODCAL = "FoodCal";
    private static final String CALORIELOG = "CalorieLog";


    private FirebaseDatabase mDatabase;
    private DatabaseReference mBaseRef;

    private DatabaseReference mProfileEndPoint;
    private DatabaseReference mPetProfileEndPoint;

    private DatabaseReference mBreakfastEndPoint;
    private DatabaseReference mLunchEndPoint;
    private DatabaseReference mDinnerEndPoint;
    private DatabaseReference mSnackEndPoint;

    private DatabaseReference mFoodCalEndPoint;
    private DatabaseReference mCalorieLogEndPoint;

    private Database() {
        mDatabase = FirebaseDatabase.getInstance();
        mBaseRef = mDatabase.getReference();

        mProfileEndPoint = mBaseRef.child(PROFILE);
        mPetProfileEndPoint = mBaseRef.child(PETPROFILE);

        mBreakfastEndPoint = mBaseRef.child(FOOD).child(BREAKFAST);
        mLunchEndPoint = mBaseRef.child(FOOD).child(LUNCH);
        mDinnerEndPoint = mBaseRef.child(FOOD).child(DINNER);
        mSnackEndPoint = mBaseRef.child(FOOD).child(SNACK);

        mFoodCalEndPoint = mBaseRef.child(FOODCAL);
        mCalorieLogEndPoint = mBaseRef.child(CALORIELOG);
    }

    public static Database getInstance() {
        return DatabaseLoader.instance;
    }

    private static class DatabaseLoader {
        private static final Database instance = new Database();
    }

    /**
     * Insert data to Profile node.
     * @param userName
     * @param dateOfBirth
     * @param height
     * @param weight
     */
    public void insertProfile(String userName, String dateOfBirth, String height, String weight) {
        Profile profile = new Profile(userName, dateOfBirth, height, weight);
        // Use setValue this way overwrites data at the specified location, including any child nodes
        mProfileEndPoint.setValue(profile);
    }

    /**
     * Update data in Profile node.
     * @param key - userName, dateOfBirth, height, weight
     * @param value - Amy, 19980101, 180, 60
     */
    public void updateProfile(String key, String value) {
        mProfileEndPoint.child(key).setValue(value);
    }

    /**
     * Add a valueEventListener to read all data in Profile node.
     * @param valueEventListener
     */
    public void addProfileListener(ValueEventListener valueEventListener) {
        mProfileEndPoint.addValueEventListener(valueEventListener);
    }

    /**
     * Remove unused valueEventListener.
     * @param valueEventListener
     */
    public void removeProfileListener(ValueEventListener valueEventListener) {
        mProfileEndPoint.removeEventListener(valueEventListener);
    }

    /**
     * Insert data to PetProfile node.
     * @param petName
     * @param petImage
     */
    public void insertPetProfile(String petName, String petImage) {
        PetProfile petProfile = new PetProfile(petName, petImage);
        mPetProfileEndPoint.setValue(petProfile);
    }

    /**
     * Update data in PetProfile node.
     * @param key
     * @param value
     */
    public void updatePetProfile(String key, String value) {
        mPetProfileEndPoint.child(key).setValue(value);
    }

    /**
     * Add a valueEventListener to read all data in PetProfile node.
     * @param valueEventListener
     */
    public void addPetProfileListener(ValueEventListener valueEventListener) {
        mPetProfileEndPoint.addValueEventListener(valueEventListener);
    }

    /**
     * Remove unused valueEventListener.
     * @param valueEventListener
     */
    public void removePetProfileListener(ValueEventListener valueEventListener) {
        mPetProfileEndPoint.removeEventListener(valueEventListener);
    }

    /**
     * Add a valueEventListener to read all data by key-value pair in mBreakfastEndPoint node.
     * @param valueEventListener
     */
    public void addBreakfastListener(ValueEventListener valueEventListener) {
        mBreakfastEndPoint.addValueEventListener(valueEventListener);
    }

    /**
     * Remove unused valueEventListener.
     * @param valueEventListener
     */
    public void removeBreakfastListener(ValueEventListener valueEventListener) {
        mBreakfastEndPoint.removeEventListener(valueEventListener);
    }

    /**
     * Add a valueEventListener to read all data by key-value pair in mLunchEndPoint node.
     * @param valueEventListener
     */
    public void addLunchListener(ValueEventListener valueEventListener) {
        mLunchEndPoint.addValueEventListener(valueEventListener);
    }

    /**
     * Remove unused valueEventListener.
     * @param valueEventListener
     */
    public void removeLunchListener(ValueEventListener valueEventListener) {
        mLunchEndPoint.removeEventListener(valueEventListener);
    }

    /**
     * Add a valueEventListener to read all data by key-value pair in mDinnerEndPoint node.
     * @param valueEventListener
     */
    public void addDinnerListener(ValueEventListener valueEventListener) {
        mDinnerEndPoint.addValueEventListener(valueEventListener);
    }

    /**
     * Remove unused valueEventListener.
     * @param valueEventListener
     */
    public void removeDinnerListener(ValueEventListener valueEventListener) {
        mDinnerEndPoint.removeEventListener(valueEventListener);
    }

    /**
     * Add a valueEventListener to read all data by key-value pair in mSnackEndPoint node.
     * @param valueEventListener
     */
    public void addSnackListener(ValueEventListener valueEventListener) {
        mSnackEndPoint.addValueEventListener(valueEventListener);
    }

    /**
     * Remove unused valueEventListener.
     * @param valueEventListener
     */
    public void removeSnackListener(ValueEventListener valueEventListener) {
        mSnackEndPoint.removeEventListener(valueEventListener);
    }

    /**
     * Add a valueEventListener to read calories of clicked food in FoodCal node.
     * @param valueEventListener
     */
    public void addClickedFoodCalListener(ValueEventListener valueEventListener) {
        mFoodCalEndPoint.addValueEventListener(valueEventListener);
    }

    /**
     * Remove unused valueEventListener.
     * @param valueEventListener
     */
    public void removeClickedFoodCalListener(ValueEventListener valueEventListener) {
        mFoodCalEndPoint.removeEventListener(valueEventListener);
    }

    /**
     * Update data in CalorieLog node.
     * @param datetime ex: 20180101
     * @param key ex: Breakfast, Lunch, Dinner, Snack
     * @param value ex: 500, 600
     */
    public void updateCalorieLog(String datetime, String key, int value) {
        mCalorieLogEndPoint.child(datetime).child(key).setValue(value + "");
    }

    /**
     * Add a valueEventListener to read calorie intake of this 'date'.
     * @param valueEventListener
     * @param date - ex:20181010
     */
    public void addDateCalorieLogListener(ValueEventListener valueEventListener, String date) {
        DatabaseReference calorieDateEndPoint = mCalorieLogEndPoint.child(date);
        calorieDateEndPoint.addValueEventListener(valueEventListener);
    }

    /**
     * Remove unused valueEventListener.
     * @param valueEventListener
     */
    public void removeDateCalorieLogListener(ValueEventListener valueEventListener, String date) {
        DatabaseReference calorieDateEndPoint = mCalorieLogEndPoint.child(date);
        calorieDateEndPoint.removeEventListener(valueEventListener);
    }

    /**
     * Add a valueEventListener to read calorie intake of a week before now.
     * @param valueEventListener
     */
    public void addWeekCalorieLogListener(ValueEventListener valueEventListener) {
        mCalorieLogEndPoint.addValueEventListener(valueEventListener);
    }

    /**
     * Remove unused valueEventListener.
     * @param valueEventListener
     */
    public void removeWeekCalorieLogListener(ValueEventListener valueEventListener) {
        mCalorieLogEndPoint.removeEventListener(valueEventListener);
    }

    /**
     * Add a valueEventListener to read calorie intake of a month before now.
     * @param valueEventListener
     */
    public void addMonthCalorieLogListener(ValueEventListener valueEventListener) {
        mCalorieLogEndPoint.addValueEventListener(valueEventListener);
    }

    /**
     * Remove unused valueEventListener.
     * @param valueEventListener
     */
    public void removeMonthCalorieLogListener(ValueEventListener valueEventListener) {
        mCalorieLogEndPoint.removeEventListener(valueEventListener);
    }
}