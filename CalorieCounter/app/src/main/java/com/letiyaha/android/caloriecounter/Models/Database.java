package com.letiyaha.android.caloriecounter.Models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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

    public void addPetProfileCallback(ValueEventListener valueEventListener) {
        mPetProfileEndPoint.addValueEventListener(valueEventListener);
    }

    public void removePetProfileCallback(ValueEventListener valueEventListener) {
        mPetProfileEndPoint.removeEventListener(valueEventListener);
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
    private void selectFood(DatabaseReference databaseReference, final SelectFoodCallback selectFoodCallback) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> foodInfo = new HashMap<String, String>();
                // Go through all key-value pairs.
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    String foodName = ds.getKey().toString(); // Get all keys alphabetically
                    String foodImage = ds.getValue().toString(); // Get all values alphabetically
                    foodInfo.put(foodName, foodImage);
                }
                selectFoodCallback.onCallback(foodInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    /**
     * To wait for database to return the data 'foodInfo'
     */
    public interface SelectFoodCallback {
        void onCallback(HashMap<String, String> foodInfo);
    }

    /**
     * Read all data in Breakfast node
     */
    public void selectBreakfast(SelectFoodCallback selectFoodCallback) {
        selectFood(mBreakfastEndPoint, selectFoodCallback);
    }

    /**
     * Read all data in Lunch node
     */
    public void selectLunch(SelectFoodCallback selectFoodCallback) {
        selectFood(mLunchEndPoint, selectFoodCallback);
    }

    /**
     * Read all data in Dinner node
     */
    public void selectDinner(SelectFoodCallback selectFoodCallback) {
        selectFood(mDinnerEndPoint, selectFoodCallback);
    }

    /**
     * Read all data in Snack node
     */
    public void selectSnack(SelectFoodCallback selectFoodCallback) {
        selectFood(mSnackEndPoint, selectFoodCallback);
    }

    /**
     * Read calories of 'foodName' in FoodCal node
     * @param foodName - ex: apple
     */
    public void selectFoodCal(final String foodName, final SelectFoodCalCallback selectFoodCalCallback) {
        mFoodCalEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String foodCal = dataSnapshot.child(foodName).getValue().toString();
                selectFoodCalCallback.onCallback(foodCal);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    /**
     * To wait for database to return the data 'foodCal'
     */
    public interface SelectFoodCalCallback {
        void onCallback(String foodCal);
    }

    /**
     * Read calories of clicked food in FoodCal node
     * @param foodClicked
     * @param selectClickedFoodCalCallback
     */
    public void seletClickedFoodCal(final HashMap<String, Boolean> foodClicked, final SelectClickedFoodCalCallback selectClickedFoodCalCallback) {
        mFoodCalEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int totalCal = 0;
                for (String foodName : foodClicked.keySet()) {
                    if (foodClicked.get(foodName)) {
                        String foodCal = dataSnapshot.child(foodName).getValue().toString();
                        totalCal += Integer.parseInt(foodCal);
                    }
                }
                selectClickedFoodCalCallback.onCallback(totalCal);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    /**
     * To wait for database to return the data 'totalCal'
     */
    public interface SelectClickedFoodCalCallback {
        void onCallback(int totalCal);
    }

    /**
     * Update data in CalorieLog node
     * @param datetime ex: 20180101
     * @param key ex: Breakfast, Lunch, Dinner, Snack
     * @param value ex: 500, 600
     */
    public void updateCalorieLog(String datetime, String key, int value) {
        mCalorieLogEndPoint.child(datetime).child(key).setValue(value + "");
    }

    /**
     * Select calorie intake of this 'date'
     * @param selectCalorieLogCallback
     * @param date - ex:20181010
     */
    public void selectCalorieLog(final SelectCalorieLogCallback selectCalorieLogCallback, String date) {
        DatabaseReference calorieDateEndPoint = mCalorieLogEndPoint.child(date);
        calorieDateEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> calInfo = new HashMap<String, String>();
                // Go through all key-value pairs.
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    String meal = ds.getKey().toString(); // Get all keys alphabetically
                    String cal = ds.getValue().toString(); // Get all values alphabetically
                    calInfo.put(meal, cal);
                }
                selectCalorieLogCallback.onCallback(calInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    /**
     * To wait for database to return the data 'calInfo'
     */
    public interface SelectCalorieLogCallback {
        void onCallback(HashMap<String, String> calInfo);
    }

    /**
     * Select calorie intake of a week before now
     * @param selectWeekCalorieLogCallback
     */
    public void selectWeekCalorieLog(final SelectWeekCalorieLogCallback selectWeekCalorieLogCallback) {
        mCalorieLogEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, Integer>> weekDateCals = new HashMap<String, HashMap<String, Integer>>();
                for (int i = 0; i < 7; i++) {
                    HashMap<String, Integer> mealCals = new HashMap<>();

                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -i);
                    Date date = cal.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    final String dateString = sdf.format(date);

                    for (DataSnapshot ds : dataSnapshot.child(dateString).getChildren()) {
                        String key = ds.getKey().toString(); // meal
                        String value = ds.getValue().toString(); // calorie
                        mealCals.put(key, Integer.parseInt(value));
                    }
                    weekDateCals.put(dateString, mealCals);
                }
                selectWeekCalorieLogCallback.onCallback(weekDateCals);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    /**
     * To wait for database to return the data 'weekDateCals'
     */
    public interface SelectWeekCalorieLogCallback {
        void onCallback(HashMap<String, HashMap<String, Integer>> weekDateCals);
    }

    /**
     * Select calorie intake of a month before now
     * @param selectMonthCalorieLogCallback
     */
    public void selectMonthCalorieLog(final SelectMonthCalorieLogCallback selectMonthCalorieLogCallback) {
        mCalorieLogEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Integer> monthDateCals = new HashMap<>();
                for (int i = 0; i < 30; i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -i);
                    Date date = cal.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    final String dateString = sdf.format(date);

                    int cals = 0;
                    for (DataSnapshot ds : dataSnapshot.child(dateString).getChildren()) {
                        cals += Integer.parseInt(ds.getValue().toString()); // calorie
                    }
                    monthDateCals.put(dateString, cals);
                }
                selectMonthCalorieLogCallback.onCallback(monthDateCals);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    /**
     * To wait for database to return the data 'monthDateCals'
     */
    public interface SelectMonthCalorieLogCallback {
        void onCallback(HashMap<String, Integer> monthDateCals);
    }
}