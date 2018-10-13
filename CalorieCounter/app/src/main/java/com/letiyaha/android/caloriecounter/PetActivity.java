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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetActivity extends AppCompatActivity {

    private Context mContext;
    private ImageView mSelectedPet;
    private String mImageSrc;

    private static final String IMAGE_PET1 = "https://cdn.pixabay.com/photo/2016/05/12/23/03/lamb-1388937__340.png";
    private static final String IMAGE_PET2 = "https://cdn.pixabay.com/photo/2016/04/01/08/29/animals-1298747__340.png";

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

        Picasso.with(mContext).load(IMAGE_PET1).into(mIvPet1);

        mIvPet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPet = mIvPet1;
                mImageSrc = IMAGE_PET1; // TODO (1)
            }
        });

        Picasso.with(mContext).load(IMAGE_PET2).into(mIvPet2);

        mIvPet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPet = mIvPet2;
                mImageSrc = IMAGE_PET2; // TODO (2)
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
