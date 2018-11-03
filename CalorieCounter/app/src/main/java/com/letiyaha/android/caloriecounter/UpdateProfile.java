package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.letiyaha.android.caloriecounter.Models.Database;
import com.letiyaha.android.caloriecounter.Models.Profile;
import com.letiyaha.android.caloriecounter.Models.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateProfile extends AppCompatActivity {

    private Context mContext;

    private static final String ICON_USER = "https://cdn.pixabay.com/photo/2016/04/26/12/25/male-1354358__480.png";
//    private static final String ICON_USER = "https://cdn.pixabay.com/photo/2016/04/15/18/05/computer-1331579__480.png";

    private static final String ICON_DOB = "https://cdn.pixabay.com/photo/2012/04/05/00/42/cake-25388__480.png";

    private static final String ICON_HEIGHT = "https://images.pexels.com/photos/162500/measurement-millimeter-centimeter-meter-162500.jpeg?auto=compress&cs=tinysrgb&h=350";

    private static final String ICON_WEIGHT = "https://images.pexels.com/photos/53404/scale-diet-fat-health-53404.jpeg?auto=compress&cs=tinysrgb&h=350";

    @BindView(R.id.iv_user_name)
    ImageView mIvUsername;

    @BindView(R.id.iv_dob)
    ImageView mIvDob;

    @BindView(R.id.iv_height)
    ImageView mIvHeight;

    @BindView(R.id.iv_weight)
    ImageView mIvWeight;

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

        Picasso.with(mContext).load(ICON_USER).into(mIvUsername);
        Picasso.with(mContext).load(ICON_DOB).into(mIvDob);
        Picasso.with(mContext).load(ICON_HEIGHT).into(mIvHeight);
        Picasso.with(mContext).load(ICON_WEIGHT).into(mIvWeight);

        // Set up hint displays
        final Database db = Database.getInstance();
        db.readProfile(new Database.ReadProfileCallback() {
            @Override
            public void onCallback(Profile profile) {
                mEtUsername.setText(profile.getUserName().toString());
                mEtDob.setText(profile.getDateOfBirth().toString());
                mEtHeight.setText(profile.getHeight().toString());
                mEtWeight.setText(profile.getWeight().toString());
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
                intentToStartPetDetailActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentToStartPetDetailActivity);
            }
        });
    }
}
