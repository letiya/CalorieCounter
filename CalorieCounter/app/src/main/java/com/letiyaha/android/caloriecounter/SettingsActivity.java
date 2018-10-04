package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    private Context mContext;

    @BindView(R.id.tv_update_profile)
    TextView mTvUpdateProfile;

    @BindView(R.id.tv_change_pet)
    TextView mTvChangePet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mContext = getApplicationContext();

        ButterKnife.bind(this);

        ActionBar actionBar = this.getSupportActionBar();
        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 1. Handle click for TextView 'Update your profile'
        mTvUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToStartUpdateProfileActivity = new Intent(mContext, UpdateProfile.class);
                startActivity(intentToStartUpdateProfileActivity);
            }
        });
        // 2. Handle click for TextView 'Change a pet'
        mTvChangePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToStartPetActivity = new Intent(mContext, PetActivity.class);
                startActivity(intentToStartPetActivity);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the PetDetailActivity
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
