package com.letiyaha.android.caloriecounter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MealDetailFragment extends Fragment {

    // Mandatory constructor for instantiating the fragment.
    public MealDetailFragment() {

    }

    /**
     * Inflates the fragment layout.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the meal detail fragment layout
        View rootview = inflater.inflate(R.layout.fragment_meal_detail, container, false);

        return rootview;
    }
}
