package com.letiyaha.android.caloriecounter;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.letiyaha.android.caloriecounter.Models.Database;
import com.letiyaha.android.caloriecounter.Models.PetProfile;
import com.squareup.picasso.Picasso;

public class PetWidgetRender {

    public PetWidgetRender() {}

    private static final String TAG = PetWidgetRender.class.getSimpleName();

    private static final String ICON_NO_PET = "https://cdn.pixabay.com/photo/2016/11/16/17/27/question-mark-1829459__480.png";

    public static void updateWidgetDisplayFromDatabase(final Context context, final BroadcastReceiver.PendingResult result) {
        final Database db = Database.getInstance();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Set up pet name/image for the widget.
                PetProfile petProfile = dataSnapshot.getValue(PetProfile.class);
                String petName = petProfile.getPetName();
                String petImage = petProfile.getPetImage();
                updateWidgetDisplay(context, petName, petImage);

                db.removePetProfileListener(this);

                if (result != null) {
                    result.finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        };
        db.addPetProfileListener(valueEventListener);
    }

    public static void updateWidgetDisplay(Context context, String petName, String petImage) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pet_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(context, PetWidgetProvider.class));

        if (petName.length() > 0) {
            views.setTextViewText(R.id.widget_pet_name, petName);
            Picasso.with(context).load(petImage).into(views, R.id.widget_pet_image, ids);
        } else {
            views.setTextViewText(R.id.widget_pet_name, "");
            Picasso.with(context).load(ICON_NO_PET).into(views, R.id.widget_pet_image, ids);
        }

        // Create an Intent to launch PetDetailActivity when clicked
        Intent intent = new Intent(context, PetDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.widget_pet_image, pendingIntent);
        appWidgetManager.updateAppWidget(ids, views);
    }
}
