package com.example.tanlam.controller.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.tanlam.R

fun showNotification(
    context: Context,
    title: String,
    content: String
) {
    Channel().createChannel(context)
    val builder = NotificationCompat.Builder(context, Channel.CHANNEL_ID)
        .setSmallIcon(R.drawable.cart_icon)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(1, builder.build())
}