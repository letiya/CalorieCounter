package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.letiyaha.android.caloriecounter.Models.Database;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealDetailAdapter extends RecyclerView.Adapter<MealDetailAdapter.MealDetailAdapterViewHolder> {

    private Context mContext;
    private Database mDb;

    private String mClickedMeal;

    private Set<Integer> mFoodClicked = new HashSet<>();

    private final MealAdapterOnClickHandler mClickHandler;

    private ArrayList<String> mFoodName = new ArrayList<String>();
    private ArrayList<String> mFoodImagePath = new ArrayList<String>();

    private static final String TAG = MealDetailAdapter.class.getSimpleName();

    private static final String BREAKFAST = "Breakfast";
    private static final String LUNCH = "Lunch";
    private static final String DINNER = "Dinner";
    private static final String SNACK = "Snack";

    public MealDetailAdapter(String clickedMeal, MealAdapterOnClickHandler handler) {
        mClickedMeal = clickedMeal;
        mClickHandler = handler;
        setFoodData(mClickedMeal);
    }

    @Override
    public MealDetailAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForListItem = R.layout.meal_detail_list_items;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MealDetailAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MealDetailAdapterViewHolder holder, int position) {
        String foodName = mFoodName.get(position);
        holder.mTvFoodName.setText(foodName);
        String foodImageUrl = mFoodImagePath.get(position);
        if (foodImageUrl != null && foodImageUrl.length() > 0) {
            Picasso.with(mContext).load(foodImageUrl).into(holder.mIvFoodImage);
        }
    }

    public void setFoodData(String clickedMeal) {
        mDb = Database.getInstance();
        switch (clickedMeal) {
            case BREAKFAST:
                ValueEventListener breakfastValueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int counter = 0;
                        // Go through all key-value pairs.
                        for (DataSnapshot ds : dataSnapshot.getChildren() ){
                            String foodName = ds.getKey().toString(); // Get all keys alphabetically
                            String foodImage = ds.getValue().toString(); // Get all values alphabetically

                            mFoodName.add(counter, foodName);
                            mFoodImagePath.add(counter, foodImage);
                            counter++;
                        }
                        notifyDataSetChanged();
                        mDb.removeBreakfastListener(this);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                };
                mDb.addBreakfastListener(breakfastValueEventListener);
                break;

            case LUNCH:
                ValueEventListener lunchValueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int counter = 0;
                        // Go through all key-value pairs.
                        for (DataSnapshot ds : dataSnapshot.getChildren() ){
                            String foodName = ds.getKey().toString(); // Get all keys alphabetically
                            String foodImage = ds.getValue().toString(); // Get all values alphabetically

                            mFoodName.add(counter, foodName);
                            mFoodImagePath.add(counter, foodImage);
                            counter++;
                        }
                        notifyDataSetChanged();
                        mDb.removeLunchListener(this);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                };
                mDb.addLunchListener(lunchValueEventListener);
                break;

            case DINNER:
                ValueEventListener dinnerValueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int counter = 0;
                        // Go through all key-value pairs.
                        for (DataSnapshot ds : dataSnapshot.getChildren() ){
                            String foodName = ds.getKey().toString(); // Get all keys alphabetically
                            String foodImage = ds.getValue().toString(); // Get all values alphabetically

                            mFoodName.add(counter, foodName);
                            mFoodImagePath.add(counter, foodImage);
                            counter++;
                        }
                        notifyDataSetChanged();
                        mDb.removeDinnerListener(this);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                };
                mDb.addDinnerListener(dinnerValueEventListener);
                break;

            case SNACK:
                ValueEventListener snackValueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int counter = 0;
                        // Go through all key-value pairs.
                        for (DataSnapshot ds : dataSnapshot.getChildren() ){
                            String foodName = ds.getKey().toString(); // Get all keys alphabetically
                            String foodImage = ds.getValue().toString(); // Get all values alphabetically

                            mFoodName.add(counter, foodName);
                            mFoodImagePath.add(counter, foodImage);
                            counter++;
                        }
                        notifyDataSetChanged();
                        mDb.removeSnackListener(this);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                };
                mDb.addSnackListener(snackValueEventListener);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mFoodName == null) {
            return 0;
        } else {
            return mFoodName.size();
        }
    }

    public class MealDetailAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_food_name)
        TextView mTvFoodName;

        @BindView(R.id.iv_image_food)
        ImageView mIvFoodImage;

        public MealDetailAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

            if (mFoodClicked.contains(adapterPosition)) {
                v.setBackgroundColor(Color.TRANSPARENT);
                mFoodClicked.remove(adapterPosition);
            } else {
                v.setBackgroundColor(mContext.getResources().getColor(R.color.lightGray));
                mFoodClicked.add(adapterPosition);
            }

            String foodName = mFoodName.get(adapterPosition);
            mClickHandler.onClick(foodName);
        }
    }
    /**
     * The interface that receives onClick messages.
     */
    public interface MealAdapterOnClickHandler {
        void onClick(String foodName);
    }
}
