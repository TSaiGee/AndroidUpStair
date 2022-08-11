package com.example.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {
    private Button normal_notify_button;
    private Button flex_notify_button;
    private Button float_notify_button;
    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;
    private NotificationCompat.Builder normal_notification_builder;
    private NotificationCompat.Builder flex_notification_builder;
    private NotificationCompat.Builder float_notification_builder;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        normal_notify_button = findViewById(R.id.normal_notify);
        flex_notify_button = findViewById(R.id.flex_notify);
        float_notify_button = findViewById(R.id.float_notify);
        init_notification_manager();
        init_normal_notification();
        init_flex_notification();
        init_float_notification();
        initController();
    }

    private void init_notification_manager() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //构造通知管道，渠道ID、名称、重要等级
        notificationChannel = new NotificationChannel("Notify", "Notify", NotificationManager.IMPORTANCE_HIGH);
        //创建通知管道
        notificationManager.createNotificationChannel(notificationChannel);

        Log.e("iKotliner", "init_notification_manager: " + notificationChannel.getImportance());

        if (notificationChannel.getImportance() != 4){
            Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, notificationChannel.getId());
            startActivity(intent);
        }
    }

    private void init_normal_notification() {
        normal_notification_builder = new NotificationCompat.Builder(this, "Notify")
                .setContentTitle("NorMal Notification")
                .setContentText("This is NorMal Notification")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0))
                .setProgress(100, 0, false);
    }

    private void init_flex_notification() {
        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.flex_notification_view);
        remoteView.setImageViewResource(R.id.icon, R.drawable.ic_launcher_foreground);
        remoteView.setTextViewText(R.id.title, "Flex Notification");
        remoteView.setTextViewText(R.id.content, "This is Flex Notification");
        flex_notification_builder = new NotificationCompat.Builder(this, "Notify")
                .setContentTitle("Flex Notification")
                .setContentText("This is Flex Notification")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setWhen(System.currentTimeMillis())
                .setCustomBigContentView(remoteView);

    }

    private void init_float_notification() {
        float_notification_builder = new NotificationCompat.Builder(this, "Notify")
                .setContentTitle("Float Notification")
                .setContentText("This is Float Notification")
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                //震动
                .setVibrate(new long[]{0,300,600})
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground))
                .setFullScreenIntent(PendingIntent.getActivity(this, 1, new Intent(), 0), true);
    }

    private void initController() {
        normal_notify_button.setOnClickListener(view -> notify_download());
        flex_notify_button.setOnClickListener(view ->
                notificationManager.notify(2, flex_notification_builder.build()));
        float_notify_button.setOnClickListener(view ->
                notificationManager.notify(3, float_notification_builder.build()));
    }

    private void notify_download() {
        progress = 10;
        notificationManager.notify(1, normal_notification_builder.setProgress(100, progress, false).build());
        Thread thread = new Thread(() -> {
            while (progress <= 100) {
                progress += 10;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                notificationManager.notify(1, normal_notification_builder.setProgress(100, progress, false).build());
            }
            notificationManager.notify(1, normal_notification_builder.setContentText("Download Complete").setProgress(0, 0, false).build());

        });
        thread.start();

        if (thread.isAlive()) {
            thread.interrupt();
        }
    }


}