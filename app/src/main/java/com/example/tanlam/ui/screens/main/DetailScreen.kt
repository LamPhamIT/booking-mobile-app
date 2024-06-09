package com.example.tanlam.ui.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.tanlam.R
import com.example.tanlam.common.isEmptyString
import com.example.tanlam.common.isValidEmail
import com.example.tanlam.controller.collection.Collections
import com.example.tanlam.controller.notification.NotificationId
import com.example.tanlam.controller.notification.showNotification
import com.example.tanlam.controller.viewmodel.DataViewModel
import com.example.tanlam.controller.viewmodel.NotificationModel
import com.example.tanlam.data.data_app.Book
import com.example.tanlam.data.notification.Notification
import com.example.tanlam.data.data_app.Order
import com.example.tanlam.data.UserInformation
import com.example.tanlam.nav.Screens
import com.example.tanlam.theme.MainGreen
import com.example.tanlam.ui.ingredients.ButtonCustom
import com.example.tanlam.ui.ingredients.TextFieldCustom
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.round

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(
    paddingValues: PaddingValues,
    book: Book,
    dataViewModel: DataViewModel,
    notificationModel: NotificationModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var typeOfPayment by remember { mutableStateOf("") }
    var showCorrectDialog by remember { mutableStateOf(false) }
    val km = book.km.toDouble()
    val context = LocalContext.current

    //Notification
    var notification by remember { mutableStateOf(Notification()) }
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference(NotificationId.NOTIFICATION_CHECK).child(book.username)
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
        notificationModel.removeNotificationForUser(book.username)

        showNotification(
            context = context,
            title = "Notification from Admin!!!!",
            content = notification.content
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(237, 237, 235))
            .padding(paddingValues)
    ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 30.dp, vertical = 10.dp),
            ) {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                        .shadow(12.dp, shape = RoundedCornerShape(40.dp))
                        .background(color = Color.White)
                        .clickable {
                            navController.popBackStack()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                        contentDescription = null
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Details",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

        Column(
            modifier = Modifier
                .weight(6f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            // Address
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 30.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.adress),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(text = book.location, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = book.destination, fontSize = 10.sp)
                }
            }

            //Infomation of truck
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 30.dp, vertical = 10.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.strucktime),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Delivery option", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.selectedicon),
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = book.nameTruck, fontSize = 12.sp)
                }
            }


            //Payment

            var isPayLater by remember { mutableStateOf(true) }
            var payLaterIcon by remember { mutableStateOf(0) }
            var payVNPAY by remember { mutableStateOf(0) }

            if (isPayLater == false) {
                payLaterIcon = R.drawable.unselected_icon
                payVNPAY = R.drawable.selectedicon
                typeOfPayment = "Pay by VNPay"
            }else {
                payLaterIcon = R.drawable.selectedicon
                payVNPAY = R.drawable.unselected_icon
                typeOfPayment = "Pay later"
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
            ) {
                Text(text = "Payment details", fontWeight = FontWeight.Medium)

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Image(
                        painter = painterResource(id = payLaterIcon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(15.dp)
                            .clickable {
                                isPayLater = !isPayLater
                            }
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "Pay later", fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Image(
                        painter = painterResource(id = payVNPAY),
                        contentDescription = null,
                        modifier = Modifier
                            .size(15.dp)
                            .clickable {
                                isPayLater = !isPayLater
                            }
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "Pay now by VNPAY", fontSize = 12.sp)
                }
            }

            // Price
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
            ) {
                Text(text = "Order summary", fontWeight = FontWeight.Medium)

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.truck_lineout),
                            contentDescription = null,
                            modifier = Modifier.size(35.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "${round(km * 10 / 10)}Km", fontWeight = FontWeight.ExtraBold)
                    }

                    Text(text = "${book.price}đ", fontWeight = FontWeight.ExtraBold)
                }
            }
        }

        //User information
        var email by remember { mutableStateOf("") }
        var firstName by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var error by remember { mutableStateOf("") }

        var orderList by remember { mutableStateOf(emptyList<Order>()) }
        var idOrder by remember { mutableStateOf(0) }
        dataViewModel.getAllOrder {
            orderList = it
        }
        for(i in orderList) {
            if(i.id > idOrder) {
                idOrder = i.id
            }
        }

        Column(
            modifier = Modifier
                .weight(6f)
                .background(Color.White)
                .fillMaxWidth()
                .padding(top = 10.dp, start = 20.dp, end = 20.dp)
        ) {
            Text(
                text = "User information",
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                TextFieldCustom(
                    title = "Email",
                    isPassWord = false,
                    onChangeValue = {
                        email = it
                    },
                    isNumber = false,
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextFieldCustom(
                        title = "First name",
                        isPassWord = false,
                        onChangeValue = {
                            firstName = it
                        },
                        isNumber = false,
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                            .weight(5f)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    TextFieldCustom(
                        title = "Name",
                        isPassWord = false,
                        onChangeValue = {
                            name = it
                        },
                        isNumber = false,
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                            .weight(5f)
                    )
                }

                TextFieldCustom(
                    title = "Phone",
                    isPassWord = false,
                    onChangeValue = {
                        phone = it
                    },
                    isNumber = true,
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                )

                Text(
                    text = error,
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total price")
                    Text(
                        text = "${book.price}đ",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 19.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                ButtonCustom(
                    title = "Place your order",
                    greenBackground = true,
                    modifier = Modifier.fillMaxWidth(),
                    onClickButton = {
                        if (!isValidEmail(email)) {
                            error = "Error email"
                        } else if (isEmptyString(firstName)) {
                            error = "invalid first name"
                        } else if (isEmptyString(name)) {
                            error = "invalid name"
                        } else if (isEmptyString(phone)) {
                            error = "invalid phone"
                        }else {
                            val userin4 = UserInformation(email, firstName, name, phone)

                            val order = Order(
                                id = ++idOrder,
                                date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                                account = book.username,
                                location = book.location,
                                destination = book.destination,
                                struckName = book.nameTruck,
                                km = book.km.toDouble(),
                                payment = typeOfPayment,
                                typeOfPlace = book.typeRoom,
                                price = book.price.toDouble(),
                                userInformation = userin4
                            )

                            dataViewModel.addData(
                                order,
                                collection = Collections.Order.name,
                                document = "${idOrder}"
                            )
                            val notification = Notification(
                                user = book.username,
                                date = LocalDateTime.now().toString(),
                                content = "${book.username} booked ${km.toInt()}km"
                            )

                            notificationModel.sendNotificationForAdmin(notification)

                            showCorrectDialog = true
                        }
                    }
                )
            }
        }
    }

    if (showCorrectDialog) {
        CorrectCheckoutDialog(
            onDismissRequest = {
                showCorrectDialog = false
                navController.navigate("${Screens.MapScreen.route}/${book.username}")
            }
        )
    }
}

@Composable
fun CorrectCheckoutDialog(
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Correct checkout",
                color = MainGreen,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Image(
                painter = painterResource(id = R.drawable.correcticon),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun testCheck() {
    CorrectCheckoutDialog {

    }
}