package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.letiyaha.android.caloriecounter.Models.Database;
import com.letiyaha.android.caloriecounter.Models.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {

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

    @BindView(R.id.bt_my_profile_next)
    Button mBtNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ButterKnife.bind(this);

        mContext = getApplicationContext();

        Picasso.with(mContext).load(ICON_USER).into(mIvUsername);
        Picasso.with(mContext).load(ICON_DOB).into(mIvDob);
        Picasso.with(mContext).load(ICON_HEIGHT).into(mIvHeight);
        Picasso.with(mContext).load(ICON_WEIGHT).into(mIvWeight);

        mBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEtUsername.getText().toString().length() == 0 || mEtDob.getText().toString().length() == 0 || mEtHeight.getText().toString().length() == 0 || mEtWeight.getText().toString().length() == 0) {
                    Toast.makeText(mContext, "Required field is blank!", Toast.LENGTH_SHORT).show();
                } else if (!Util.isDateValid(mEtDob.getText().toString(), "yyyy/MM/dd")) {
                    Toast.makeText(mContext, "Date of birth format is incorrect!", Toast.LENGTH_SHORT).show();
                } else if (!Util.isNumber(mEtHeight.getText().toString())) {
                    Toast.makeText(mContext, "Entered height is not a number!", Toast.LENGTH_SHORT).show();
                } else if (!Util.isNumber(mEtWeight.getText().toString())) {
                    Toast.makeText(mContext, "Entered weight is not a number!", Toast.LENGTH_SHORT).show();
                } else {
                    // 1. Save data to db
                    Database db = Database.getInstance();
                    db.insertProfile(mEtUsername.getText().toString(), mEtDob.getText().toString(), mEtHeight.getText().toString(), mEtWeight.getText().toString());
                    // 2. Go to PetActivity.
                    Intent intentToStartPetActivity = new Intent(mContext, PetActivity.class);
                    startActivity(intentToStartPetActivity);
                }
            }
        });
    }
}
