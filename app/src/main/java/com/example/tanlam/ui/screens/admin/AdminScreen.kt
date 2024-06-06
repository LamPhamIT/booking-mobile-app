package com.example.tanlam.ui.screens.admin

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.tanlam.controller.notification.NotificationId
import com.example.tanlam.controller.notification.showNotification
import com.example.tanlam.controller.viewmodel.DataViewModel
import com.example.tanlam.controller.viewmodel.NotificationModel
import com.example.tanlam.data.data_app.Order
import com.example.tanlam.data.notification.Notification
import com.example.tanlam.ui.ingredients.ButtonCustom
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminScreen(
    dataViewModel: DataViewModel,
    notificationModel: NotificationModel,
    context: Context,
    paddingValues: PaddingValues
) {
    var listOrder by remember { mutableStateOf(emptyList<Order>()) }
    var orderOfUser by remember { mutableStateOf(Order()) }
    var showDialog by remember { mutableStateOf(false) }

    dataViewModel.getAllOrder {
        listOrder = it
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Admin",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        var notification by remember { mutableStateOf(Notification()) }
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(NotificationId.NOTIFICATION_ORDER)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(Notification::class.java)
                if(value != null) {
                    notification = value
                }else {
                    notification = Notification()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        if(notification != Notification()) {
            notificationModel.removeNotificationForAdmin()

            showNotification(
                context = context,
                title = "Order!!!!!",
                content = notification.content
            )
        }

        LazyColumn {
            items(listOrder.size) { index ->
                AdminContent(
                    listOrder[index],
                    onClickValue = {
                        orderOfUser = listOrder[index]
                        showDialog = true
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

    if (showDialog) {
        DialogAdmin(
            order = orderOfUser,
            notificationModel = notificationModel,
            onOrderChange = {
                orderOfUser = it
            },
            ondeleteOrder = {
                dataViewModel.deleteOrder(it.id.toString())
            },
            onDismissRequest = {
                showDialog = false
            }
        )
    }
}

@Composable
fun AdminContent(
    order: Order,
    onClickValue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .clickable {
                onClickValue()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Name: ",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = order.userInformation.name)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Price: ",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "${order.price}vnd")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DialogAdmin(
    order: Order,
    ondeleteOrder:(Order) -> Unit,
    notificationModel: NotificationModel,
    onOrderChange: (Order) -> Unit,
    onDismissRequest: () -> Unit
) {
    var notification by remember { mutableStateOf(Notification()) }

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        Column(
            modifier = Modifier.background(Color.Gray, shape = RoundedCornerShape(20.dp))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Name: ",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = order.userInformation.name)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Km: ",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = order.km.toString())
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Location: ",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = order.location)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Destination: ",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = order.destination)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Price: ",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "${order.price}vnd")
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    ButtonCustom(
                        title = "Confirm",
                        greenBackground = true,
                        onClickButton = {
                            order.isConfirm = true
                            onOrderChange(order)
                            ondeleteOrder(order)
                            onDismissRequest()

                            notification = Notification(
                                user = order.account,
                                date = LocalDateTime.now().toString(),
                                content = "Your order from ${order.location} to ${order.destination} has been approved"
                            )
                            notificationModel.sendNotificationForUser(order.account, notification)
                        }
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    ButtonCustom(
                        title = "Reject",
                        greenBackground = false,
                        onClickButton = {
                            order.isConfirm = false
                            onOrderChange(order)
                            ondeleteOrder(order)
                            onDismissRequest()

                            notification = Notification(
                                user = order.account,
                                date = LocalDateTime.now().toString(),
                                content = "Your order from ${order.location} to ${order.destination} hasn't been approved"
                            )
                            notificationModel.sendNotificationForUser(order.account, notification)
                        }
                    )
                }
            }
        }
    }
}