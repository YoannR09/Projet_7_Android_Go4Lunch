package com.example.go4lunch.workers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.go4lunch.Go4LunchApplication;
import com.example.go4lunch.R;
import com.example.go4lunch.repo.Repositories;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DinerWorker extends Worker {

    public DinerWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            boolean notif = sharedPref.getBoolean("saved_notification", true);
            if(notif) {
                Repositories.getDinerRepository().getDinerFromWorkmate(data -> {
                    createNotificationChannel();
                    if(data != null) {
                        Repositories.getDinerRepository().getListDinersFromRestaurant(listDiners -> {
                            String notifText;
                            if (listDiners.size() != 0) {
                                notifText = getApplicationContext().getString(R.string.alone_to_diner);
                            } else {
                                notifText = getApplicationContext().getString(R.string.diner_with_mate)
                                        + listDiners.size()
                                        + getApplicationContext().getString(R.string.mates);
                            }
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                                    .setSmallIcon(R.drawable.ic_baseline_local_dining_24)
                                    .setContentTitle(getApplicationContext().getString(R.string.time_to_lunch))
                                    .setContentText(notifText)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            NotificationManagerCompat notificationManager
                                    = NotificationManagerCompat.from(getApplicationContext());
                            notificationManager.notify(44, builder.build());
                        }, data.getRestaurantId());
                    } else {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                                .setSmallIcon(R.drawable.ic_baseline_local_dining_24)
                                .setContentTitle(getApplicationContext().getString(R.string.time_to_lunch))
                                .setContentText(getApplicationContext().getString(R.string.select_restaurant))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        NotificationManagerCompat notificationManager
                                = NotificationManagerCompat.from(getApplicationContext());
                        notificationManager.notify(44, builder.build());
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void start() {
        PeriodicWorkRequest.Builder myWorkBuilder =
                new PeriodicWorkRequest
                        .Builder(DinerWorker.class, 16, TimeUnit.MINUTES)
                        .setInitialDelay(1000, TimeUnit.MILLISECONDS);

        PeriodicWorkRequest myWork = myWorkBuilder.build();
        WorkManager.getInstance(Go4LunchApplication.getContext())
                .enqueueUniquePeriodicWork("dinerWorker", ExistingPeriodicWorkPolicy.REPLACE, myWork);
    }

    /**
     * Use this to call first time notification at twelve.
     * @return int time to ms
     */
    public int getInitDelayMs() {
        Date date = new Date();
        if(date.getHours() > 12) {
            int hourDiff = 36 - date.getHours();
            return hourDiff * 60 * 60 * 1000;
        } else {
            int hourDiff = 12 - date.getHours();
            return hourDiff * 60 * 60  * 1000;
        }
    }
}
