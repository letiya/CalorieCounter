package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.letiyaha.android.caloriecounter.Models.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

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
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mContext = getApplicationContext();

        mBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEtUsername.getText().toString().length() == 0 || mEtDob.getText().toString().length() == 0 || mEtHeight.getText().toString().length() == 0 || mEtWeight.getText().toString().length() == 0) {
                    Toast.makeText(mContext, "Required field is blank!", Toast.LENGTH_SHORT).show();
                } else if (!isDateValid(mEtDob.getText().toString(), "yyyy/MM/dd")) {
                    Toast.makeText(mContext, "Date of birth format is incorrect!", Toast.LENGTH_SHORT).show();
                } else if (!isNumber(mEtHeight.getText().toString())) {
                    Toast.makeText(mContext, "Entered height is not a number!", Toast.LENGTH_SHORT).show();
                } else if (!isNumber(mEtWeight.getText().toString())) {
                    Toast.makeText(mContext, "Entered weight is not a number!", Toast.LENGTH_SHORT).show();
                } else {
                    // 1. Save data to db
                    Database db = Database.getInstance();
                    db.insertProfile(mEtUsername.getText().toString(), mEtDob.getText().toString(), mEtHeight.getText().toString(), mEtWeight.getText().toString());
                    // 2. Start another activity.
                    Intent intentToStartPetActivity = new Intent(mContext, PetActivity.class);
                    startActivity(intentToStartPetActivity);
                }
            }
        });
    }

    /**
     * To verify if input date follows the format.
     * @param dateToValidate
     * @param dateFromat - ex: 2018/10/01
     * @return true if dateToValidate follows the format.
     */
    private boolean isDateValid(String dateToValidate, String dateFromat) {
        if(dateToValidate == null){
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);
        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * To verify input string is a number.
     * @param string
     * @return true if input string is a number.
     */
    private boolean isNumber(String string) {
        try {
            double d = Double.parseDouble(string);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}
