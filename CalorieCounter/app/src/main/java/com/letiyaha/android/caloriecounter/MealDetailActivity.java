package com.letiyaha.android.caloriecounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MealDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        Intent intentThatStartedThisActivity = getIntent();
        String mealClicked = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

        MealDetailFragment mealDetailFragment = new MealDetailFragment();
        mealDetailFragment.setClickedMeal(mealClicked);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.meal_detail_container, mealDetailFragment)
                .commit();
    }

}
