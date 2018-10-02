package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.letiyaha.android.caloriecounter.Models.Database;
import com.letiyaha.android.caloriecounter.Models.PetProfile;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetDetailActivity extends AppCompatActivity {

    private Context mContext;

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.tv_pet_name)
    TextView mTvPetname;

    @BindView(R.id.iv_pet)
    ImageView mIvPet;

    @BindView(R.id.bt_feed_me)
    Button mBtFeedMe;

    @BindView(R.id.bt_pet_health)
    Button mBtPetHealth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        ButterKnife.bind(this);

        mContext = getApplicationContext();

//        setSupportActionBar(mToolbar);

        // 1. Set up pet name/image.
        Database db = Database.getInstance();
        db.readPetProfile(new Database.ReadPetProfileCallback() {
            @Override
            public void onCallback(PetProfile petProfile) {
                mTvPetname.setText(" " + petProfile.getPetName());
                if (petProfile.getPetImage().equals("")) { // TODO (3)

                } else { // TODO (4)

                }
            }
        });

        // 2. Set up button 'Feed Me'
        mBtFeedMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToStartMealActivity = new Intent(mContext, MealActivity.class);
                startActivity(intentToStartMealActivity);
            }
        });

        // 3. Set up button 'My health condition'
        mBtPetHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToStartMyHealthActivity = new Intent(mContext, HealthActivity.class);
                startActivity(intentToStartMyHealthActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu_pet_detail, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
