package com.letiyaha.android.caloriecounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.letiyaha.android.caloriecounter.Charts.DrawBarChart;
import com.letiyaha.android.caloriecounter.Charts.DrawLineChart;
import com.letiyaha.android.caloriecounter.Charts.DrawPieChart;
import com.letiyaha.android.caloriecounter.Models.Database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HealthActivity extends AppCompatActivity {

    private Database mDb;
    private ValueEventListener mDateValueEventListener;
    private ValueEventListener mWeekValueEventListener;
    private ValueEventListener mMonthValueEventListener;

    private SimpleDateFormat mSdf = new SimpleDateFormat("yyyyMMdd");
    private String mToday = mSdf.format(new Date());

    private static final String TAG = HealthActivity.class.getSimpleName();

    @BindView(R.id.pc_day)
    PieChart mPieChart;

    @BindView(R.id.bc_week)
    BarChart mBarChart;

    @BindView(R.id.lc_month)
    LineChart mLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_stats);

        ButterKnife.bind(this);

        mDb = Database.getInstance();

        // 1. - Draw a pie chart for today.
        mDateValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Integer> dataValueList = new HashMap<String, Integer>();
                // Go through all key-value pairs.
                for (DataSnapshot ds : dataSnapshot.getChildren() ) {
                    String meal = ds.getKey().toString(); // Get all keys alphabetically
                    String cal = ds.getValue().toString(); // Get all values alphabetically
                    dataValueList.put(meal, Integer.parseInt(cal));
                }

                if (dataValueList.size() != 0) {
                    DrawPieChart drawPieChart = new DrawPieChart(mPieChart, dataValueList, mToday);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        };

        // 2. - Draw a week bar chart.
        mWeekValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, Integer>> weekDateCals = new HashMap<String, HashMap<String, Integer>>();
                for (int i = 0; i < 7; i++) {
                    HashMap<String, Integer> mealCals = new HashMap<>();

                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -i);
                    Date date = cal.getTime();
                    final String dateString = mSdf.format(date);

                    for (DataSnapshot ds : dataSnapshot.child(dateString).getChildren()) {
                        String key = ds.getKey().toString(); // meal
                        String value = ds.getValue().toString(); // calorie
                        mealCals.put(key, Integer.parseInt(value));
                    }
                    weekDateCals.put(dateString, mealCals);
                }
                drawWeekBarchart(weekDateCals);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        };

        // 3. - Draw a month line chart.
        mMonthValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Integer> monthDateCals = new HashMap<>();
                for (int i = 0; i < 30; i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -i);
                    Date date = cal.getTime();
                    final String dateString = mSdf.format(date);

                    int cals = 0;
                    for (DataSnapshot ds : dataSnapshot.child(dateString).getChildren()) {
                        cals += Integer.parseInt(ds.getValue().toString()); // calorie
                    }
                    monthDateCals.put(dateString, cals);
                }
                drawMonthLinechart(monthDateCals);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        };
    }

    /**
     * Draw a bar chart for calorie intake in a past week.
     * @param weekDateCals
     */
    private void drawWeekBarchart(HashMap<String, HashMap<String, Integer>> weekDateCals) {
        // Sort by date -
        Map<String, HashMap<String, Integer>> sortedDateCals = new TreeMap<String, HashMap<String, Integer>>(weekDateCals);

        LinkedHashMap<String, List<Float>> dataLists = new LinkedHashMap<>();
        List<String> xAxisValues = new ArrayList<>(); // 20180101
        List<Float> yBreakfastVal = new ArrayList<>(); // Breakfast
        List<Float> yLunchVal = new ArrayList<>(); // Lunch
        List<Float> yDinnerVal = new ArrayList<>(); // Dinner
        List<Float> ySnackVal = new ArrayList<>(); // Snack
        List<Integer> colors = Arrays.asList(getResources().getColor(R.color.yellow), getResources().getColor(R.color.orange), getResources().getColor(R.color.darkBlue), getResources().getColor(R.color.blue));

        for (String date : sortedDateCals.keySet()) {
            HashMap<String, Integer> mealCal = sortedDateCals.get(date);

            if (mealCal.size() > 0) {
                float breakfastCal = 0;
                float lunchCal = 0;
                float dinnerCal = 0;
                float snackCal = 0;

                if (mealCal.get("breakfastCal") != null) {
                    breakfastCal = (float) mealCal.get("breakfastCal");
                }
                if (mealCal.get("lunchCal") != null) {
                    lunchCal = (float) mealCal.get("lunchCal");
                }
                if (mealCal.get("dinnerCal") != null) {
                    dinnerCal = (float) mealCal.get("dinnerCal");
                }
                if (mealCal.get("snackCal") != null) {
                    snackCal = (float) mealCal.get("snackCal");
                }

                xAxisValues.add(date.substring(4));
                yBreakfastVal.add(breakfastCal + lunchCal + dinnerCal + snackCal);
                yLunchVal.add(lunchCal + dinnerCal + snackCal);
                yDinnerVal.add(dinnerCal + snackCal);
                ySnackVal.add(snackCal);
            }
        }
        dataLists.put("Breakfast", yBreakfastVal);
        dataLists.put("Lunch", yLunchVal);
        dataLists.put("Dinner", yDinnerVal);
        dataLists.put("Snack", ySnackVal);

        if (xAxisValues.size() != 0) {
            DrawBarChart drawBarChart = new DrawBarChart(mBarChart, xAxisValues, dataLists, colors);
        }
    }

    /**
     * Draw a line chart for calorie intake in a past month.
     * @param monthDateCals
     */
    private void drawMonthLinechart(HashMap<String, Integer> monthDateCals) {
        // Sort by date -
        Map<String, Integer> sortedDateCals = new TreeMap<String, Integer>(monthDateCals);

        List<String> xAxisValues = new ArrayList<>(); // 20180101
        List<Float> dateValueList = new ArrayList<>();

        for (String date : sortedDateCals.keySet()) {
            int dateCal = 0;
            if (sortedDateCals.get(date) != null) {
                dateCal = sortedDateCals.get(date);
            }
            xAxisValues.add(date.substring(4));
            dateValueList.add((float) dateCal);
        }
        DrawLineChart drawLineChart = new DrawLineChart(xAxisValues, mLineChart, dateValueList, "Calorie intake");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDb.addDateCalorieLogListener(mDateValueEventListener, mToday);
        mDb.addWeekCalorieLogListener(mWeekValueEventListener);
        mDb.addMonthCalorieLogListener(mMonthValueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDb.removeDateCalorieLogListener(mDateValueEventListener, mToday);
        mDb.removeWeekCalorieLogListener(mWeekValueEventListener);
        mDb.removeMonthCalorieLogListener(mMonthValueEventListener);
    }
}
