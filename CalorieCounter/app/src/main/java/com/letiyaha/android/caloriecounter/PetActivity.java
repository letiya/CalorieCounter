package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.letiyaha.android.caloriecounter.Models.Database;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetActivity extends AppCompatActivity {

    private Context mContext;
    private ImageView mSelectedPet;
    private String mImageSrc;

    @BindView(R.id.et_petname)
    EditText mEtPetname;

    @BindView(R.id.iv_pet1)
    ImageView mIvPet1;

    @BindView(R.id.iv_pet2)
    ImageView mIvPet2;

    @BindView(R.id.bt_choose_pet_next)
    Button mBtNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        ButterKnife.bind(this);

        mContext = getApplicationContext();

        mIvPet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPet = mIvPet1;
                mImageSrc = ""; // TODO (1)
            }
        });

        mIvPet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPet = mIvPet2;
                mImageSrc = ""; // TODO (2)
            }
        });

        mBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEtPetname.getText().toString().length() == 0) {
                    Toast.makeText(mContext, "Pet name is blank!", Toast.LENGTH_SHORT).show();
                } else if (mSelectedPet == null) {
                    Toast.makeText(mContext, "Please select a pet!", Toast.LENGTH_SHORT).show();
                } else {
                    // 1. Save data to db
                    Database db = Database.getInstance();
                    db.insertPetProfile(mEtPetname.getText().toString(), mImageSrc);
                    // 2. Start another activity.
                    Intent intentToStartPetDetailActivity = new Intent(mContext, PetDetailActivity.class);
                    startActivity(intentToStartPetDetailActivity);
                }
            }
        });
    }
}
