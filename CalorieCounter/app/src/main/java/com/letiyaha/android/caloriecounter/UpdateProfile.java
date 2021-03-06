package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.letiyaha.android.caloriecounter.Models.Database;
import com.letiyaha.android.caloriecounter.Models.Profile;
import com.letiyaha.android.caloriecounter.Models.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateProfile extends AppCompatActivity {

    private Context mContext;
    private Database mDb;
    private ValueEventListener mValueEventListener;

    private static final String TAG = UpdateProfile.class.getSimpleName();

    private static final String USERNAME = "userName";
    private static final String DATEOFBIRTH = "dateOfBirth";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";

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

        mDb = Database.getInstance();

        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                mEtUsername.setText(profile.getUserName().toString());
                mEtDob.setText(profile.getDateOfBirth().toString());
                mEtHeight.setText(profile.getHeight().toString());
                mEtWeight.setText(profile.getWeight().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        };

        // Handle button 'Finish' click
        mBtFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean updateSucceed = true;
                // 1. Update data in db
                if (mEtUsername.getText().toString().length() != 0) {
                    mDb.updateProfile(USERNAME, mEtUsername.getText().toString());
                }
                if (mEtDob.getText().toString().length() != 0) {
                    if (Util.isDateValid(mEtDob.getText().toString(), "yyyy/MM/dd")) {
                        mDb.updateProfile(DATEOFBIRTH, mEtDob.getText().toString());
                    } else {
                        updateSucceed = false;
                        Toast.makeText(mContext, getString(R.string.warning_dob_format_incorrect), Toast.LENGTH_SHORT).show();
                    }
                }
                if (mEtHeight.getText().toString().length() != 0) {
                    if (Util.isNumber(mEtHeight.getText().toString())) {
                        mDb.updateProfile(HEIGHT, mEtHeight.getText().toString());
                    } else {
                        updateSucceed = false;
                        Toast.makeText(mContext, getString(R.string.warning_height_type_incorrect), Toast.LENGTH_SHORT).show();
                    }
                }
                if (mEtWeight.getText().toString().length() != 0) {
                    if (Util.isNumber(mEtWeight.getText().toString())) {
                        mDb.updateProfile(WEIGHT, mEtWeight.getText().toString());
                    } else {
                        updateSucceed = false;
                        Toast.makeText(mContext, getString(R.string.warning_weight_type_incorrect), Toast.LENGTH_SHORT).show();
                    }
                }
                if (updateSucceed) {
                    // 2. Go back to PetDetailActivity.
                    Intent intentToStartPetDetailActivity = new Intent(mContext, PetDetailActivity.class);
                    intentToStartPetDetailActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intentToStartPetDetailActivity);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mDb.addProfileListener(mValueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDb.removeProfileListener(mValueEventListener);
    }
}
