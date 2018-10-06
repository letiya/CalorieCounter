package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealFragment extends Fragment {

    private Context mContext;

    private static final String BREAKFAST = "Breakfast";
    private static final String LUNCH = "Lunch";
    private static final String DINNER = "Dinner";
    private static final String SNACK = "Snack";

    @BindView(R.id.tv_breakfast)
    TextView mTvBreakfast;

    @BindView(R.id.tv_lunch)
    TextView mTvLunch;

    @BindView(R.id.tv_dinner)
    TextView mTvDinner;

    @BindView(R.id.tv_snack)
    TextView mTvSnack;

    // Mandatory constructor for instantiating the fragment.
    public MealFragment() {

    }

    /**
     * Inflates the fragment layout.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the meal fragment layout
        View rootView = inflater.inflate(R.layout.fragment_meal, container, false);

        ButterKnife.bind(this, rootView);

        mContext = rootView.getContext();

        // 1. Set up click for 'Breakfast'
        mTvBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnMealClicked(BREAKFAST);
            }
        });

        // 2. Set up click for 'Lunch'
        mTvLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnMealClicked(LUNCH);
            }
        });

        // 3. Set up click for 'Dinner'
        mTvDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnMealClicked(DINNER);
            }
        });

        // 4. Set up click for 'Snack'
        mTvSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnMealClicked(SNACK);
            }
        });

        return rootView;
    }

    public interface OnMealClickListener {
        void OnMealClicked(String mealClicked);
    }

    OnMealClickListener mCallback;

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnMealClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMealClickListener");
        }
    }
}