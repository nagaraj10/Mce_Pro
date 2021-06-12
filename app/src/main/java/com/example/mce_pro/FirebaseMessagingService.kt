package com.example.mce_pro

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService:FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if(remoteMessage.data!=null){
            val title=remoteMessage.notification!!.title
            val body=remoteMessage.notification!!.body

            NotificationHelper.displaynotification(applicationContext,title!!,body!!)

        }

    }
}
