package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.letiyaha.android.caloriecounter.Models.Database;
import com.letiyaha.android.caloriecounter.Models.PetProfile;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext;
    private Database mDb;
    private ValueEventListener mValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        mDb = Database.getInstance();

        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PetProfile petProfile = dataSnapshot.getValue(PetProfile.class);
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

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mDb.addPetProfileListener(mValueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDb.removePetProfileListener(mValueEventListener);
        finish();
    }
}
