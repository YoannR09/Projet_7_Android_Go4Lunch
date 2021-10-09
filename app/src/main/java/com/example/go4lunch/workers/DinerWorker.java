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
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.go4lunch.Go4LunchApplication;
import com.example.go4lunch.R;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.viewModel.DinerViewModel;
import com.example.go4lunch.ui.viewModel.ui.WorkerViewModel;

import java.util.concurrent.TimeUnit;

import static android.provider.Settings.System.getString;

public class DinerWorker extends Worker {

    private static long msToTwelve (){
        return 1000;
    }

    WorkerViewModel viewModel = new WorkerViewModel();

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
                viewModel.loadDinerFromWorkmate();
                Thread.sleep(100);
                DinerViewModel diner = viewModel.getCurrentDiner();
                if(diner != null) {
                    viewModel.loadDinersListFromRestaurantId(diner.getRestaurantId());
                    Thread.sleep(100);
                    createNotificationChannel();
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                            .setSmallIcon(R.drawable.ic_baseline_local_dining_24)
                            .setContentTitle("Hey, its time to lunch !")
                            .setContentText("You have actually "+ viewModel.getCurrentDiners().size()
                            + "mates eating on same restaurant")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager
                            = NotificationManagerCompat.from(getApplicationContext());
                    notificationManager.notify(44, builder.build());
                }
            }
        } catch (InterruptedException e) {
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
}
