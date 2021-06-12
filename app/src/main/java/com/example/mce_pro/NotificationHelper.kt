package com.example.mce_pro

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

object NotificationHelper {
    fun displaynotification(context: Context,title:String,body:String){
        val Intent= Intent(context,MainActivity::class.java)
        val pendingIntent=PendingIntent.getActivity(context,100,Intent,PendingIntent.FLAG_CANCEL_CURRENT)
        val mBuilder=NotificationCompat.Builder(context,MainActivity.CHANNEL_ID)
            .setSmallIcon(R.mipmap.mce1)
            .setColor(ContextCompat.getColor(context,R.color.blue))
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val notificationmgr=NotificationManagerCompat.from(context)
        notificationmgr.notify(1,mBuilder.build())


    }
}