package com.example.customnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int notification_id;
    private RemoteViews remoteViews;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);

        remoteViews = new RemoteViews(getPackageName(),R.layout.custom_notification);
        remoteViews.setImageViewResource(R.id.notif_icon,R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.notif_title,"TEXT");
        remoteViews.setProgressBar(R.id.progressBar,100,40,true);

        Button bSetNotification = findViewById(R.id.a_main_bNotification);
        bSetNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notification_id =  (int) System.currentTimeMillis();

                Intent button_intent = new Intent("button_clicked");
                button_intent.putExtra("id",notification_id);

                PendingIntent p_button_intent = PendingIntent.getBroadcast(context,notification_id,button_intent,0);
                remoteViews.setOnClickPendingIntent(R.id.button,p_button_intent);


                Intent notificationIntent = new Intent(context,MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context,0,notificationIntent,0);

                builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setCustomBigContentView(remoteViews)
                        .setContentIntent(pendingIntent);

                notificationManager.notify(notification_id,builder.build());
            }
        });
    }
}
