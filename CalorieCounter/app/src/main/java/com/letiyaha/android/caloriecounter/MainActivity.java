package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.letiyaha.android.caloriecounter.Models.Database;
import com.letiyaha.android.caloriecounter.Models.PetProfile;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        Database db = Database.getInstance();

        db.readPetProfile(new Database.ReadPetProfileCallback() {
            @Override
            public void onCallback(PetProfile petProfile) {
                if (petProfile.getPetName().length() > 0) {
                    // Go to PetDetailActivity.
                    Intent intentToStartPetDetailActivity = new Intent(mContext, PetDetailActivity.class);
                    startActivity(intentToStartPetDetailActivity);
                } else {
                    // Go to UserActivity.
                    Intent intentToStartUserActivity = new Intent(mContext, UserActivity.class);
                    startActivity(intentToStartUserActivity);
                }
            }
        });
    }
}
