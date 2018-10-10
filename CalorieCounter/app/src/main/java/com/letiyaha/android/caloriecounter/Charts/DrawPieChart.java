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

        if (dataValueList.get("breakfastCal") != null && dataValueList.get("breakfastCal") != 0) {
            entries.add(new PieEntry(dataValueList.get("breakfastCal"), "Breakfast"));
        }
        if (dataValueList.get("lunchCal") != null && dataValueList.get("lunchCal") != 0) {
            entries.add(new PieEntry(dataValueList.get("lunchCal"), "Lunch"));
        }
        if (dataValueList.get("dinnerCal") != null && dataValueList.get("dinnerCal") != 0) {
            entries.add(new PieEntry(dataValueList.get("dinnerCal"), "Dinner"));
        }
        if (dataValueList.get("snackCal") != null && dataValueList.get("snackCal") != 0) {
            entries.add(new PieEntry(dataValueList.get("snackCal"), "Snack"));
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
