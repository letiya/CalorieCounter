package com.letiyaha.android.caloriecounter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PetDetailFragment extends Fragment {

    // Mandatory constructor for instantiating the fragment.
    public PetDetailFragment() {

    }

    /**
     * Inflates the fragment layout.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the pet detail fragment layout
        View rootView = inflater.inflate(R.layout.fragment_pet_detail, container, false);

        return rootView;

    }
}