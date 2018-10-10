package com.letiyaha.android.caloriecounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.letiyaha.android.caloriecounter.Charts.DrawBarChart;
import com.letiyaha.android.caloriecounter.Charts.DrawLineChart;
import com.letiyaha.android.caloriecounter.Charts.DrawPieChart;
import com.letiyaha.android.caloriecounter.Models.Database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HealthActivity extends AppCompatActivity {

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

        Database db = Database.getInstance();

        // 1. - Draw a pie chart for today.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String today = sdf.format(new Date());

        db.selectCalorieLog(new Database.SelectCalorieLogCallback() {
            @Override
            public void onCallback(HashMap<String, String> calInfo) {
                HashMap<String, Integer> dataValueList = new HashMap<String, Integer>();
                for (String meal : calInfo.keySet()) {
                    dataValueList.put(meal, Integer.parseInt(calInfo.get(meal)));
                }
                if (dataValueList.size() != 0) {
                    DrawPieChart drawPieChart = new DrawPieChart(mPieChart, dataValueList, today);
                }
            }
        }, today);

        // 2. - Draw a week bar chart.
        db.selectWeekCalorieLog(new Database.SelectWeekCalorieLogCallback() {
            @Override
            public void onCallback(HashMap<String, HashMap<String, Integer>> weekDateCals) {
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
        });

        // 3. - Draw a month line chart.
        db.selectMonthCalorieLog(new Database.SelectMonthCalorieLogCallback() {
            @Override
            public void onCallback(HashMap<String, Integer> monthDateCals) {
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
        });
    }
}
