package com.letiyaha.android.caloriecounter;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

public class PetWidgetProvider extends AppWidgetProvider {
    private PendingResult mResult;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        mResult = goAsync();
        PetWidgetRender.updateWidgetDisplayFromDatabase(context, mResult);
    }

}
