package com.example.tanlam.controller.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.tanlam.controller.notification.NotificationId
import com.example.tanlam.data.notification.Notification
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class NotificationModel(): ViewModel() {
    fun sendNotificationForAdmin(notification: Notification) {
        val testDatabase = FirebaseDatabase.getInstance().getReference()
        val test: Query = testDatabase.child(NotificationId.NOTIFICATION_ORDER)
        test.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.ref.setValue(notification)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun removeNotificationForAdmin() {
        val testDatabase = FirebaseDatabase.getInstance().getReference()
        val test: Query = testDatabase.child(NotificationId.NOTIFICATION_ORDER)
        test.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.ref.removeValue()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun sendNotificationForUser(
        userName: String,
        notification: Notification
    ) {
        val testDatabase = FirebaseDatabase.getInstance().getReference()
        val test: Query = testDatabase.child(NotificationId.NOTIFICATION_CHECK).child(userName)
        test.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.ref.setValue(notification)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun removeNotificationForUser(
        userName: String
    ) {
        val testDatabase = FirebaseDatabase.getInstance().getReference()
        val test = testDatabase.child(NotificationId.NOTIFICATION_CHECK).child(userName)

        test.removeValue()
    }
}