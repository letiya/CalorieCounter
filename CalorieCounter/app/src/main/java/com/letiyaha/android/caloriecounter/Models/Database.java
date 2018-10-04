package com.letiyaha.android.caloriecounter.Models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {

    private static final String TAG = Database.class.getSimpleName();

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

        mProfileEndPoint = mBaseRef.child("Profile");
        mPetProfileEndPoint = mBaseRef.child("PetProfile");

        mBreakfastEndPoint = mBaseRef.child("Food").child("Breakfast");
        mLunchEndPoint = mBaseRef.child("Food").child("Lunch");
        mDinnerEndPoint = mBaseRef.child("Food").child("Dinner");
        mSnackEndPoint = mBaseRef.child("Food").child("Snack");

        mFoodCalEndPoint = mBaseRef.child("FoodCal");
        mCalorieLogEndPoint = mBaseRef.child("CalorieLog");
    }

    public static Database getInstance() {
        return DatabaseLoader.instance;
    }

    private static class DatabaseLoader {
        private static final Database instance = new Database();
    }

    /**
     * Insert data to Profile node
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
     * Update data in Profile node
     * @param key - userName, dateOfBirth, height, weight
     * @param value - Amy, 19980101, 180, 60
     */
    public void updateProfile(String key, String value) {
        mProfileEndPoint.child(key).setValue(value);
    }

    /**
     * Read all data in Profile node
     */
    public void readProfile(final ReadProfileCallback readProfileCallback) {
        mProfileEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                readProfileCallback.onCallback(profile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    /**
     * To wait for database to return the data 'Profile'
     */
    public interface ReadProfileCallback {
        void onCallback(Profile profile);
    }

    /**
     * Insert data to PetProfile node
     * @param petName
     * @param petImage
     */
    public void insertPetProfile(String petName, String petImage) {
        PetProfile petProfile = new PetProfile(petName, petImage);
        mPetProfileEndPoint.setValue(petProfile);
    }

    /**
     * Update data in PetProfile node
     * @param key
     * @param value
     */
    public void updatePetProfile(String key, String value) {
        mPetProfileEndPoint.child(key).setValue(value);
    }

    /**
     * Read all data in PetProfile node
     */
    public void readPetProfile(final ReadPetProfileCallback readPetProfileCallback) {
        mPetProfileEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PetProfile petProfile = dataSnapshot.getValue(PetProfile.class);
                readPetProfileCallback.onCallback(petProfile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    /**
     * To wait for database to return the data 'PetProfile'
     */
    public interface ReadPetProfileCallback {
        void onCallback(PetProfile petProfile);
    }

    /**
     * Read all data key-value pair by key-value pair in databaseReference node
     * @param databaseReference
     */
    private void selectFood(DatabaseReference databaseReference) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Go through all key-value pairs.
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    String foodName = ds.getKey().toString(); // Get all keys alphabetically
                    String foodImage = ds.getValue().toString(); // Get all values alphabetically
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    /**
     * Read all data in Breakfast node
     */
    public void selectBreakfast() {
        selectFood(mBreakfastEndPoint);
    }

    /**
     * Read all data in Lunch node
     */
    public void selectLunch() {
        selectFood(mLunchEndPoint);
    }

    /**
     * Read all data in Dinner node
     */
    public void selectDinner() {
        selectFood(mDinnerEndPoint);
    }

    /**
     * Read all data in Snack node
     */
    public void selectSnack() {
        selectFood(mSnackEndPoint);
    }

    /**
     * Read calorie of 'foodName' in FoodCal node
     * @param foodName - ex: apple
     */
    public void selectFoodCal(final String foodName) {
        mFoodCalEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String foodCal = dataSnapshot.child(foodName).getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    /**
     * Update data in CalorieLog node
     * @param datetime ex: 20180101
     * @param key ex: Breakfast, Lunch, Dinner, Snack
     * @param value ex: 500, 600
     */
    public void updateCalorieLog(String datetime, String key, int value) {
        mCalorieLogEndPoint.child(datetime).child(key).setValue(value);
    }

    /**
     * Read calorie intake on 'date' in CalorieLog node
     * @param date - ex: 20180101
     */
    public void selectOneDayCalorie(String date) {
        mCalorieLogEndPoint.child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CalorieLog calorieLog = dataSnapshot.getValue(CalorieLog.class);
                calorieLog.sum();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }
}