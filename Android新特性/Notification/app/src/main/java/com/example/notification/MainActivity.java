package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button normal_notify_button;
    private Button flex_notify_button;
    private Button float_notify_button;
    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;
    private Notification normal_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        normal_notify_button = findViewById(R.id.normal_notify);
        flex_notify_button = findViewById(R.id.flex_notify);
        float_notify_button = findViewById(R.id.float_notify);
        init_narmal_notification();
        initController();
        notificationManager.notify(1,normal_notification);
    }

    private void init_narmal_notification() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //构造通知管道，渠道ID、名称、重要等级
        notificationChannel = new NotificationChannel("Notify","Notify",NotificationManager.IMPORTANCE_HIGH);
        //创建通知管道
        notificationManager.createNotificationChannel(notificationChannel);

        normal_notification = new NotificationCompat.Builder(this,"Notify")
                .setContentTitle("Notification")
                .setContentText("This is Notification")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_foreground).build();
    }

    private void initController() {
        normal_notify_button.setOnClickListener(view -> notificationManager.notify(1,normal_notification));
    }





}