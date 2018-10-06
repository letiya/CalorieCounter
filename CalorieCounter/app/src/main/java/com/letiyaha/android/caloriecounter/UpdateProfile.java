package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.letiyaha.android.caloriecounter.Models.Database;
import com.letiyaha.android.caloriecounter.Models.Profile;
import com.letiyaha.android.caloriecounter.Models.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateProfile extends AppCompatActivity {

    private Context mContext;

    @BindView(R.id.et_username)
    EditText mEtUsername;

    @BindView(R.id.et_dob)
    EditText mEtDob;

    @BindView(R.id.et_height)
    EditText mEtHeight;

    @BindView(R.id.et_weight)
    EditText mEtWeight;

    @BindView(R.id.bt_my_profile_finish)
    Button mBtFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        ButterKnife.bind(this);

        mContext = getApplicationContext();

        // Set up hint displays
        final Database db = Database.getInstance();
        db.readProfile(new Database.ReadProfileCallback() {
            @Override
            public void onCallback(Profile profile) {
                mEtUsername.setHint(profile.getUserName().toString());
                mEtDob.setHint(profile.getDateOfBirth().toString());
                mEtHeight.setHint(profile.getHeight().toString());
                mEtWeight.setHint(profile.getWeight().toString());
            }
        });

        // Handle button 'Finish' click
        mBtFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Update data in db
                if (mEtUsername.getText().toString().length() != 0) {
                    db.updateProfile("userName", mEtUsername.getText().toString());
                }
                if (mEtDob.getText().toString().length() != 0 && Util.isDateValid(mEtDob.getText().toString(), "yyyy/MM/dd")) {
                    db.updateProfile("dateOfBirth", mEtDob.getText().toString());
                }
                if (mEtHeight.getText().toString().length() != 0 && Util.isNumber(mEtHeight.getText().toString())) {
                    db.updateProfile("height", mEtHeight.getText().toString());
                }
                if (mEtWeight.getText().toString().length() != 0 && Util.isNumber(mEtWeight.getText().toString())) {
                    db.updateProfile("weight", mEtWeight.getText().toString());
                }
                // 2. Go back to PetDetailActivity.
                Intent intentToStartPetDetailActivity = new Intent(mContext, PetDetailActivity.class);
                startActivity(intentToStartPetDetailActivity);
            }
        });
    }
}
