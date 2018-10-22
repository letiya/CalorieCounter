package com.letiyaha.android.caloriecounter;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.letiyaha.android.caloriecounter.Models.Database;
import com.letiyaha.android.caloriecounter.Models.PetProfile;
import com.squareup.picasso.Picasso;

public class PetWidgetRender {

    public PetWidgetRender() {

    }

    private static final String ICON_NO_PET = "https://cdn.pixabay.com/photo/2016/11/16/17/27/question-mark-1829459__480.png";

    public static void updateWidgetDisplay(final Context context, final RemoteViews views, AppWidgetManager appWidgetManager, final int appWidgetId) {
        // Set up pet name/image for the widget.
        Database db = Database.getInstance();
        db.readPetProfile(new Database.ReadPetProfileCallback() {
            @Override
            public void onCallback(PetProfile petProfile) {
                String petName = petProfile.getPetName();
                String petImage = petProfile.getPetImage();

                int[] appWidgetIds = {appWidgetId};

                if (petName.length() > 0) {
                    views.setTextViewText(R.id.widget_pet_name, petName);

                    // Set up pet image for the widget.
//                    views.setImageViewResource(R.id.widget_pet_image, R.drawable.ic_launcher_foreground);

                    Picasso.with(context)
                            .load(petImage)
                            .into(views, R.id.widget_pet_image, appWidgetIds);

                } else {
                    views.setTextViewText(R.id.widget_pet_name, "");

                    // Set up pet image for the widget.
//                    views.setImageViewResource(R.id.widget_pet_image, R.drawable.ic_launcher_foreground);

                    Picasso.with(context)
                            .load(ICON_NO_PET)
                            .into(views, R.id.widget_pet_image, appWidgetIds);

                }
            }
        });
    }

    public static void updateWidgetDisplays(final Context context, AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pet_widget);
        for (int appWidgetId : appWidgetIds) {
            updateWidgetDisplay(context, views, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager, final int appWidgetId) {
        // Create an Intent to launch PetDetailActivity when clicked
        Intent intent = new Intent(context, PetDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pet_widget);

        updateWidgetDisplay(context, views, appWidgetManager, appWidgetId);

        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.widget_pet_image, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}
