package com.letiyaha.android.caloriecounter;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MealActivity extends AppCompatActivity implements MealFragment.OnMealClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        MealFragment mealFragment = new MealFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.meal_container, mealFragment)
                .commit();
    }

    @Override
    public void OnMealClicked(String mealClicked) {
        Intent intentToStartMeailDetailActivity = new Intent(this, MealDetailActivity.class);
        intentToStartMeailDetailActivity.putExtra(Intent.EXTRA_TEXT, mealClicked);
        startActivity(intentToStartMeailDetailActivity);
    }
}
