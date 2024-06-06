package com.example.tanlam.controller.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tanlam.controller.collection.Collections
import com.example.tanlam.data.data_app.Account
import com.example.tanlam.data.data_app.Order
import com.example.tanlam.data.data_app.Struck
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class DataViewModel() : ViewModel() {
    fun addData(
        data: Any,
        collection: String,
        document: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val fireStoreRef = Firebase.firestore
                .collection(collection)
                .document(document)

            try {
                fireStoreRef.set(data).await()
            } catch (e: Exception) {
                println("Lỗi khi thêm tài khoản: ${e.message}")
            }
        }
    }

    //ACCOUNT
    fun getAllAccount(listAccount: (List<Account>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val fireStoreRef = Firebase.firestore.collection("Accounts")

            try {
                val querySnapshot = fireStoreRef.get().await()
                val listOfAccount = mutableListOf<Account>()

                for (document in querySnapshot) {
                    val account = document.toObject(Account::class.java)
                    listOfAccount.add(account)
                }

                listAccount(listOfAccount)
            } catch (e: Exception) {
                // Xử lý lỗi khi có vấn đề xảy ra
                Log.e("getAllAccount", "Error getting documents: ", e)
                listAccount(emptyList())
            }
        }
    }

    fun retrieveAccount(
        userName: String,
        data: (Account) -> Unit,
        onError: (Exception) -> Unit = {}
    ) = CoroutineScope(Dispatchers.IO).launch {
        val fireStoreRef = Firebase.firestore
            .collection(Collections.Account.name)
            .document(userName)
        try {
            fireStoreRef.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    onError(e)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val data = snapshot.toObject(Account::class.java)
                    if (data != null) {
                        CoroutineScope(Dispatchers.Main).launch {
                            data(data)
                        }
                    }
                }
            }
        }catch (e: Exception) {
            onError(e)
        }
    }

    fun uploadImageAccount(
        userName: String,
        bitmap: Bitmap,
        callBack:(Boolean, String) -> Unit
    ) {
        val storage = Firebase.storage.reference
        val imageRef = storage.child("Accounts/$userName")

        val bao = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao) //  luu bitmap vao baos
        val imageData = bao.toByteArray()

        imageRef.putBytes(imageData).addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {uri ->
                val imageUrl = uri.toString()
                callBack(true, imageUrl)
            }.addOnFailureListener{
                callBack(false, null.toString())
            }
        }.addOnFailureListener{
            callBack(false, null.toString())
        }
    }

    //STRUCK
    fun retrieveStruck(
        struckId : Int,
        data: (Struck) -> Unit,
        onError: (Exception) -> Unit = {}
    ) = CoroutineScope(Dispatchers.IO).launch {
        val fireStoreRef = Firebase.firestore
            .collection(Collections.Struck.name)
            .document(struckId.toString())
        try {
            fireStoreRef.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    onError(e)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val data = snapshot.toObject(Struck::class.java)
                    if (data != null) {
                        CoroutineScope(Dispatchers.Main).launch {
                            data(data)
                        }
                    }
                }
            }
        }catch (e: Exception) {
            onError(e)
        }
    }


    //ORDER
    fun getAllOrder(listAccount: (List<Order>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val fireStoreRef = Firebase.firestore.collection(Collections.Order.name)

            try {
                val querySnapshot = fireStoreRef.get().await()
                val listOfAccount = mutableListOf<Order>()

                for (document in querySnapshot) {
                    val order = document.toObject(Order::class.java)
                    listOfAccount.add(order)
                }

                listAccount(listOfAccount)
            } catch (e: Exception) {
                // Xử lý lỗi khi có vấn đề xảy ra
                Log.e("getAllAccount", "Error getting documents: ", e)
            }
        }
    }

    fun deleteOrder(
        id: String,
    ) = CoroutineScope(Dispatchers.IO).launch{
        val fireStoreRef = Firebase.firestore
            .collection(Collections.Order.name)
            .document(id)

        try {
            fireStoreRef.delete().await()
        }catch (e: Exception) {
            println("error")
        }
    }
}