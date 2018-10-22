package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealFragment extends Fragment {

    private Context mContext;

    private static final String BREAKFAST = "Breakfast";
    private static final String LUNCH = "Lunch";
    private static final String DINNER = "Dinner";
    private static final String SNACK = "Snack";

//    private static final String ICON_BREAKFAST = "https://images.pexels.com/photos/920220/pexels-photo-920220.jpeg?auto=compress&cs=tinysrgb&h=350";
//    private static final String ICON_BREAKFAST = "https://cdn.pixabay.com/photo/2014/12/15/13/40/croissants-569075__480.jpg";
    private static final String ICON_BREAKFAST = "https://cdn.pixabay.com/photo/2015/06/24/01/15/morning-819362__480.jpg";

    private static final String ICON_LUNCH = "https://cdn.pixabay.com/photo/2014/10/19/20/59/hamburger-494706__480.jpg";
    private static final String ICON_DINNER = "https://cdn.pixabay.com/photo/2015/09/02/12/43/meal-918639__480.jpg";
    private static final String ICON_SNACK = "https://cdn.pixabay.com/photo/2016/11/29/06/17/black-coffee-1867753__480.jpg";


    @BindView(R.id.iv_breakfast)
    ImageView mIvBreakfast;

    @BindView(R.id.tv_breakfast)
    TextView mTvBreakfast;

    @BindView(R.id.iv_lunch)
    ImageView mIvLunch;

    @BindView(R.id.tv_lunch)
    TextView mTvLunch;

    @BindView(R.id.iv_dinner)
    ImageView mIvDinner;

    @BindView(R.id.tv_dinner)
    TextView mTvDinner;

    @BindView(R.id.iv_snack)
    ImageView mIvSnack;

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

        Picasso.with(mContext).load(ICON_BREAKFAST).into(mIvBreakfast);
        Picasso.with(mContext).load(ICON_LUNCH).into(mIvLunch);
        Picasso.with(mContext).load(ICON_DINNER).into(mIvDinner);
        Picasso.with(mContext).load(ICON_SNACK).into(mIvSnack);

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