package com.letiyaha.android.caloriecounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.letiyaha.android.caloriecounter.Models.Database;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealDetailAdapter extends RecyclerView.Adapter<MealDetailAdapter.MealDetailAdapterViewHolder> {

    private Context mContext;

    private String mClickedMeal;

    private final MealAdapterOnClickHandler mClickHandler;

    private ArrayList<String> mFoodName = new ArrayList<String>();
    private ArrayList<String> mFoodImagePath = new ArrayList<String>();

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
        Database db = Database.getInstance();
        switch (clickedMeal) {
            case BREAKFAST:
                db.selectBreakfast(new Database.SelectFoodCallback() {
                    @Override
                    public void onCallback(HashMap<String, String> foodInfo) {
                        int counter = 0;
                        for (String key : foodInfo.keySet()) {
                            mFoodName.add(counter, key);
                            mFoodImagePath.add(counter, foodInfo.get(key));
                            counter++;
                        }
                        notifyDataSetChanged();
                    }
                });
                break;

            case LUNCH:
                db.selectLunch(new Database.SelectFoodCallback() {
                    @Override
                    public void onCallback(HashMap<String, String> foodInfo) {
                        int counter = 0;
                        for (String key : foodInfo.keySet()) {
                            mFoodName.add(counter, key);
                            mFoodImagePath.add(counter, foodInfo.get(key));
                            counter++;
                        }
                        notifyDataSetChanged();
                    }
                });
                break;

            case DINNER:
                db.selectDinner(new Database.SelectFoodCallback() {
                    @Override
                    public void onCallback(HashMap<String, String> foodInfo) {
                        int counter = 0;
                        for (String key : foodInfo.keySet()) {
                            mFoodName.add(counter, key);
                            mFoodImagePath.add(counter, foodInfo.get(key));
                            counter++;
                        }
                        notifyDataSetChanged();
                    }
                });
                break;

            case SNACK:
                db.selectSnack(new Database.SelectFoodCallback() {
                    @Override
                    public void onCallback(HashMap<String, String> foodInfo) {
                        int counter = 0;
                        for (String key : foodInfo.keySet()) {
                            mFoodName.add(counter, key);
                            mFoodImagePath.add(counter, foodInfo.get(key));
                            counter++;
                        }
                        notifyDataSetChanged();
                    }
                });
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
