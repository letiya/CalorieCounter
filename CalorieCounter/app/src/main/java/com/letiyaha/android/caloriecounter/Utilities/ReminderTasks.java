package com.letiyaha.android.caloriecounter.Utilities;

import android.content.Context;

public class ReminderTasks {

    public static final String ACTION_EAT_REMINDER = "eat-reminder";

    public static void executeTask(Context context, String action) {
        if (ACTION_EAT_REMINDER.equals(action)) {
            issueEatReminder(context);
        }
    }

    private static void issueEatReminder(Context context) {
        NotificationUtils.remindUserToEat(context);
    }
}
