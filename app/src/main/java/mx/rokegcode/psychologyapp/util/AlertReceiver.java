package mx.rokegcode.psychologyapp.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification("Responder escuenta", "Por favor responda las siguientes preguntas");
        notificationHelper.getManager().notify(1, nb.build());
    }
}
