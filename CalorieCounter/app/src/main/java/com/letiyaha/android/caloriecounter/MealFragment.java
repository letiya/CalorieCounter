package com.letiyaha.android.caloriecounter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MealFragment extends Fragment {

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

        return rootView;
    }
}