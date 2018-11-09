package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.letiyaha.android.caloriecounter.Models.PetProfile;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetActivity extends AppCompatActivity {

    private Context mContext;
    private Database mDb;
    private ValueEventListener mPetValueEventListener;
    private ImageView mSelectedPet;
    private String mImageSrc;

    private static final String TAG = PetActivity.class.getSimpleName();

    private static final String IMAGE_PET1 = "https://cdn.pixabay.com/photo/2016/05/12/23/03/lamb-1388937__340.png";
    private static final String IMAGE_PET2 = "https://cdn.pixabay.com/photo/2016/04/01/08/29/animals-1298747__340.png";
    private static final String IMAGE_PET3 = "https://cdn.pixabay.com/photo/2014/10/02/15/43/zebra-470305__480.png";
    private static final String IMAGE_PET4 = "https://cdn.pixabay.com/photo/2014/10/04/22/29/monkey-474147__480.png";

    @BindView(R.id.iv_pet)
    ImageView mIvPet;

    @BindView(R.id.et_petname)
    EditText mEtPetname;

    @BindView(R.id.iv_pet1)
    ImageView mIvPet1;

    @BindView(R.id.iv_pet2)
    ImageView mIvPet2;

    @BindView(R.id.iv_pet3)
    ImageView mIvPet3;

    @BindView(R.id.bt_choose_pet_next)
    Button mBtNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        ButterKnife.bind(this);

        mContext = getApplicationContext();

        mDb = Database.getInstance();

        // Default pet image
        mSelectedPet = mIvPet1;
        mImageSrc = IMAGE_PET1;
        Picasso.with(mContext).load(mImageSrc).into(mIvPet);

        // Default pet name
        mPetValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PetProfile petProfile = dataSnapshot.getValue(PetProfile.class);
                if (petProfile.getPetName().length() > 0) {
                    mEtPetname.setText(petProfile.getPetName().toString());
                } else {
                    mEtPetname.setHint(getString(R.string.hint_name_pet));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        };

        Picasso.with(mContext).load(IMAGE_PET1).into(mIvPet1);
        mIvPet1.setContentDescription(getString(R.string.content_description_sheep));

        mIvPet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPet = mIvPet1;
                mImageSrc = IMAGE_PET1;
                Picasso.with(mContext).load(mImageSrc).into(mIvPet);
            }
        });

        Picasso.with(mContext).load(IMAGE_PET2).into(mIvPet2);
        mIvPet2.setContentDescription(getString(R.string.content_description_turtle));

        mIvPet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPet = mIvPet2;
                mImageSrc = IMAGE_PET2;
                Picasso.with(mContext).load(mImageSrc).into(mIvPet);
            }
        });

        Picasso.with(mContext).load(IMAGE_PET3).into(mIvPet3);
        mIvPet3.setContentDescription(getString(R.string.content_description_zibra));

        mIvPet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPet = mIvPet3;
                mImageSrc = IMAGE_PET3;
                Picasso.with(mContext).load(mImageSrc).into(mIvPet);
            }
        });

        mBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEtPetname.getText().toString().length() == 0) {
                    Toast.makeText(mContext, getString(R.string.warning_pet_name_blank), Toast.LENGTH_SHORT).show();
                } else if (mSelectedPet == null) {
                    Toast.makeText(mContext, getString(R.string.warning_select_a_pet), Toast.LENGTH_SHORT).show();
                } else {
                    // 1. Save data to db
                    Database db = Database.getInstance();
                    db.insertPetProfile(mEtPetname.getText().toString(), mImageSrc);

                    // 2. Update widget image
                    PetWidgetRender.updateWidgetDisplay(mContext, mEtPetname.getText().toString(), mImageSrc);

                    // 3. Start PetDetail Activity.
                    Intent intentToStartPetDetailActivity = new Intent(mContext, PetDetailActivity.class);
                    intentToStartPetDetailActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intentToStartPetDetailActivity);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDb.addPetProfileListener(mPetValueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDb.removePetProfileListener(mPetValueEventListener);
    }
}
