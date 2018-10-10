package com.letiyaha.android.caloriecounter.Charts;

import android.support.annotation.ColorRes;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DrawBarChart {

    private BarChart mBarChart;
    private XAxis xAxis;
    private YAxis leftYAxis;
    private YAxis rightYAxis;
    private Legend legend;

    public DrawBarChart(BarChart barChart, List<String> xAxisValues, LinkedHashMap<String, List<Float>> dataLists, List<Integer> colors) {

        mBarChart = barChart;

        initBarChart();
        showBarChart(xAxisValues, dataLists, colors);
    }

    private void initBarChart() {
        // Hide background gridlines
//        mBarChart.getAxisLeft().setDrawGridLines(false);
//        mBarChart.getXAxis().setDrawGridLines(false);
        // Background shadow
        mBarChart.setDrawBarShadow(false);
        mBarChart.setHighlightFullBarEnabled(false);
        // Chart border
        mBarChart.setDrawBorders(true);

        mBarChart.getDescription().setEnabled(false);

        // Set up X, Y axis.
        xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        leftYAxis = mBarChart.getAxisLeft();

        rightYAxis = mBarChart.getAxisRight();
        rightYAxis.setEnabled(false);

        // Set up legend
        legend = mBarChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(8f);
        // legend position
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        legend.setDrawInside(false);
    }

    private void initBarDataSet(BarDataSet barDataSet, int color) {
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        // Hide bar top value
        barDataSet.setDrawValues(false);
    }

    private void showBarChart(final List<String> xAxisValues, LinkedHashMap<String, List<Float>> dataLists, @ColorRes List<Integer> colors) {
        List<IBarDataSet> dataSets = new ArrayList<>();
        int currentPosition = 0;

        for (LinkedHashMap.Entry<String, List<Float>> entry : dataLists.entrySet()) {
            String name = entry.getKey();
            List<Float> dataValueList = entry.getValue();

            List<BarEntry> entries = new ArrayList<>();

            for (int i = 0; i < dataValueList.size(); i++) {
                entries.add(new BarEntry(i, dataValueList.get(i)));
            }

            BarDataSet barDataSet = new BarDataSet(entries, name);
            initBarDataSet(barDataSet, colors.get(currentPosition));
            dataSets.add(barDataSet);

            currentPosition++;
        }

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisValues.get((int) value % xAxisValues.size());
            }
        });

        BarData data = new BarData(dataSets);
        mBarChart.setData(data);
    }

    private void showBarChart(List<Float> dataValueList, String name, int color) {
        List<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < dataValueList.size(); i++) {
            BarEntry barEntry = new BarEntry(i, dataValueList.get(i));
            entries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(entries, name);
        initBarDataSet(barDataSet, color);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.5f);
        mBarChart.setData(data);
    }
}
