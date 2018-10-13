package com.letiyaha.android.caloriecounter;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.letiyaha.android.caloriecounter.Models.Database;
import com.letiyaha.android.caloriecounter.Models.PetProfile;
import com.squareup.picasso.Picasso;

public class PetWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager, final int appWidgetId) {
        // Create an Intent to launch PetDetailActivity when clicked
        Intent intent = new Intent(context, PetDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pet_widget);


        Database db = Database.getInstance();
        db.readPetProfile(new Database.ReadPetProfileCallback() {
            @Override
            public void onCallback(PetProfile petProfile) {
                String petName = petProfile.getPetName();
                String petImage = petProfile.getPetImage();

                int[] appWidgetIds = {appWidgetId};

                Picasso.with(context)
                        .load(petImage)
                        .into(views, R.id.widget_pet_image, appWidgetIds);
            }
        });



        // TODO (5)
        // Set up pet name for the widget.
        views.setTextViewText(R.id.widget_pet_name, "XXX");
        // Set up pet image for the widget.
        views.setImageViewResource(R.id.widget_pet_image, R.drawable.ic_launcher_foreground);

        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.widget_pet_image, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

}
