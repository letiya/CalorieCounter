package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.letiyaha.android.caloriecounter.Models.Database;

import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealDetailFragment extends Fragment implements MealDetailAdapter.MealAdapterOnClickHandler {

    private Context mContext;
    private Database mDb;

    private String mClickedMeal;

    private MealDetailAdapter mMealDetailAdapter;

    private HashMap<String, Boolean> mFoodClicked = new HashMap<String, Boolean>();

    private static final String TAG = MealDetailFragment.class.getSimpleName();

    @BindView(R.id.recyclerview_food) RecyclerView mRecyclerView;

    @BindView(R.id.bt_add_to_plate)
    Button mBtAdd2Plate;

    // Mandatory constructor for instantiating the fragment.
    public MealDetailFragment() {}

    public void setClickedMeal(String clickedMeal) {
        mClickedMeal = clickedMeal;
    }

    /**
     * Inflates the fragment layout.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the meal detail fragment layout
        View rootview = inflater.inflate(R.layout.fragment_meal_detail, container, false);

        mContext = rootview.getContext();

        ButterKnife.bind(this, rootview);

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, getColumnNum());

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMealDetailAdapter = new MealDetailAdapter(mClickedMeal, this);

        mRecyclerView.setAdapter(mMealDetailAdapter);

        mDb = Database.getInstance();

        // Handle button click 'Add to plate'
        mBtAdd2Plate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get total calorie intake and update database
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int totalCal = 0;
                        for (String foodName : mFoodClicked.keySet()) {
                            if (mFoodClicked.get(foodName)) {
                                String foodCal = dataSnapshot.child(foodName).getValue().toString();
                                totalCal += Integer.parseInt(foodCal);
                            }
                        }
                        mDb.removeClickedFoodCalListener(this);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        mDb.updateCalorieLog(sdf.format(new Date()), mClickedMeal.toLowerCase() + "Cal", totalCal);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                };
                mDb.addClickedFoodCalListener(valueEventListener);

                // Go back to PetDetailActivity.
                Intent intentToStartPetDetailActivity = new Intent(mContext, PetDetailActivity.class);
                intentToStartPetDetailActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentToStartPetDetailActivity);
            }
        });

        return rootview;
    }

    @Override
    public void onClick(String foodName) {
        if (mFoodClicked.containsKey(foodName)) {
            mFoodClicked.put(foodName, !mFoodClicked.get(foodName));
        } else {
            mFoodClicked.put(foodName, true);
        }
    }

    /**
     * Dynamically calculate the number of columns and the layout would adapt to the screen size and orientation
     * @return number of columns in the grid for GridLayoutManager.
     */
    private int getColumnNum() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }
}
