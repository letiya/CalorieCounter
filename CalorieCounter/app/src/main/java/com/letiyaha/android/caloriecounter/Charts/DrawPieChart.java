package com.letiyaha.android.caloriecounter.Charts;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrawPieChart {

    private PieChart mPieChart;

    private static final String BREAKFAST_CAL = "breakfastCal";
    private static final String LUNCH_CAL = "lunchCal";
    private static final String DINNER_CAL = "dinnerCal";
    private static final String SNACK_CAL = "snackCal";

    private static final String LABEL_BREAKFAST = "Breakfast";
    private static final String LABEL_LUNCH = "Lunch";
    private static final String LABEL_DINNER = "Dinner";
    private static final String LABEL_SNACK = "Snack";

    public DrawPieChart(PieChart pieChart, HashMap<String, Integer> dataValueList, String chartName) {
        mPieChart = pieChart;

        initPieChart();
        showPieChart(dataValueList, chartName);
    }

    private void initPieChart() {
        mPieChart.getDescription().setEnabled(false);
        // Hide textual description inside the pie-slices
        mPieChart.setDrawSliceText(false);
        //Disable Hole in the Pie Chart
        mPieChart.setDrawHoleEnabled(false);
        mPieChart.invalidate(); // refresh
    }

    private void initPieDataSet(PieDataSet pieDataSet, int[] colors) {
        pieDataSet.setColors(colors);
    }

    private void showPieChart(HashMap<String, Integer> dataValueList, String name) {
        // Get chart data
        List<PieEntry> entries = new ArrayList<>();

        if (dataValueList.get(BREAKFAST_CAL) != null && dataValueList.get(BREAKFAST_CAL) != 0) {
            entries.add(new PieEntry(dataValueList.get(BREAKFAST_CAL), LABEL_BREAKFAST));
        }
        if (dataValueList.get(LUNCH_CAL) != null && dataValueList.get(LUNCH_CAL) != 0) {
            entries.add(new PieEntry(dataValueList.get(LUNCH_CAL), LABEL_LUNCH));
        }
        if (dataValueList.get(DINNER_CAL) != null && dataValueList.get(DINNER_CAL) != 0) {
            entries.add(new PieEntry(dataValueList.get(DINNER_CAL), LABEL_DINNER));
        }
        if (dataValueList.get(SNACK_CAL) != null && dataValueList.get(SNACK_CAL) != 0) {
            entries.add(new PieEntry(dataValueList.get(SNACK_CAL), LABEL_SNACK));
        }

        // Get Pie data
        PieDataSet pieDataSet = new PieDataSet(entries, name);
        initPieDataSet(pieDataSet, ColorTemplate.LIBERTY_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(13f);
        pieData.setValueTextColor(Color.BLACK);

        mPieChart.setData(pieData);
    }
}
