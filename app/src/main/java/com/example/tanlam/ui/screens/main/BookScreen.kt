package com.example.tanlam.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tanlam.R
import com.example.tanlam.ShareLiveDataModel
import com.example.tanlam.controller.notification.NotificationId
import com.example.tanlam.controller.notification.showNotification
import com.example.tanlam.controller.viewmodel.NotificationModel
import com.example.tanlam.data.notification.Notification
import com.example.tanlam.nav.Screens
import com.example.tanlam.theme.MainGreen
import com.example.tanlam.ui.ingredients.ButtonCustom
import com.example.tanlam.ui.ingredients.MapUI
import com.example.tanlam.ui.ingredients.getMapLocation
import com.example.tanlam.ui.ingredients.rememberMapViewWithLifecycle
import com.google.android.gms.maps.MapView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.round

@Composable
fun BookScreen(
    paddingValues: PaddingValues,
    username: String,
    location: String,
    destination: String,
    date: String,
    notificationModel: NotificationModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    shareLiveDataModel: ShareLiveDataModel = viewModel()
) {
    val mapView = rememberMapViewWithLifecycle()
    var km by remember { mutableStateOf(0.0) }
    var totalPrice by remember { mutableStateOf(0) }

    val context = LocalContext.current

    LaunchedEffect (Unit){ // => Chi cho cai map xuat hien 1 lan thoi -> Du co thay reset dao dien thi no van kh reset
        getMapLocation(location, destination, context, mapView, getKm = {
            km = it
        })
    }

    //Notification
    var notification by remember { mutableStateOf(Notification()) }
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference(NotificationId.NOTIFICATION_CHECK).child(username)
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
        notificationModel.removeNotificationForUser(username)

        showNotification(
            context = context,
            title = "Notification from Admin!!!!",
            content = notification.content
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(237, 237, 235))
            .padding(paddingValues),
        contentAlignment = Alignment.BottomCenter
    ) {
        MapUI(
            mapView = mapView,
            context = context,
            clickToFindAddress = false,
            shareLiveDataModel = shareLiveDataModel
        )

        var isShowBottomSelected by remember { mutableStateOf(true) }
        var heightOfBottom by remember { mutableStateOf(520.dp) }

        if (isShowBottomSelected) {
            heightOfBottom = 520.dp
        } else {
            heightOfBottom = 150.dp
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 20.dp)
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
            }

            Column(
                modifier = Modifier
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                    )
                    .height(heightOfBottom)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                IconButton(onClick = { isShowBottomSelected = !isShowBottomSelected }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_double_arrow_down_24),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }


                //Time and Day
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = "Estimated time",
                        fontSize = 23.sp,
                        color = Color(139, 139, 139),
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
//                        Text(
//                            text = "9:50PM",
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Medium
//                        )
                        Text(text = date)
                    }
                }

                // Truck
                var isSelectedSmallTruck by remember { mutableStateOf(true) }
                var typeOfTruck by remember { mutableStateOf("") }
                var colorBackGroundTruck by remember { mutableStateOf(Color(239, 249, 250)) }
                var colorBackGroundContainer by remember { mutableStateOf(Color.White) }

                if (isSelectedSmallTruck) {
                    colorBackGroundTruck = Color(239, 249, 250)
                    colorBackGroundContainer = Color.White
                    typeOfTruck = "Small truck (1000kg - 5000kg)"
                    totalPrice = (50000 * km).toInt()
                } else {
                    colorBackGroundTruck = Color.White
                    colorBackGroundContainer = Color(239, 249, 250)
                    typeOfTruck = "Large truck (5000kg - 10000kg)"
                    totalPrice = (100000 * km).toInt()
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorBackGroundTruck)
                            .height(60.dp)
                            .clickable {
                                isSelectedSmallTruck = true
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.truck_lineout),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )

                            Text(
                                text = "Small truck (1000kg - 5000kg)",
                                fontSize = 12.sp
                            )
                            Text(
                                text = "${(km * 50000).toInt()}đ/${round(km * 10 / 10)}km",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(217, 217, 217))
                            .height(1.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(colorBackGroundContainer)
                            .clickable {
                                isSelectedSmallTruck = false
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.container),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )

                            Text(
                                text = "Large truck (5000kg - 10000kg)",
                                fontSize = 12.sp
                            )
                            Text(
                                text = "${(km * 100000).toInt()}đ/${round(km) * 10 / 10}km", fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }


                //TypeRoom
                var isSelectedRoom by remember { mutableStateOf(true) }
                var colorBackGroundRoom by remember { mutableStateOf(MainGreen) }
                var colorBackGroundHome by remember { mutableStateOf(Color.White) }
                var colorTextRoom by remember { mutableStateOf(MainGreen) }
                var colorTextHome by remember { mutableStateOf(Color.White) }

                var typeOfPlace by remember { mutableStateOf("") }

                if (isSelectedRoom) {
                    colorBackGroundRoom = MainGreen
                    colorBackGroundHome = Color.White

                    colorTextRoom = Color.White
                    colorTextHome = Color(106, 100, 100)

                    typeOfPlace = "Room"
                } else {
                    colorBackGroundRoom = Color.White
                    colorBackGroundHome = MainGreen

                    colorTextRoom = Color(106, 100, 100)
                    colorTextHome = Color.White

                    typeOfPlace = "Entire home"
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                ) {
                    Text(
                        text = "Type of place",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .border(
                                2.dp,
                                color = Color(217, 217, 217),
                                shape = RoundedCornerShape(30.dp)
                            )
                            .clip(RoundedCornerShape(30.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(5f)
                                .background(color = colorBackGroundRoom)
                                .fillMaxSize()
                                .clickable {
                                    isSelectedRoom = true
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Room",
                                color = colorTextRoom,
                                fontSize = 17.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(5f)
                                .background(color = colorBackGroundHome)
                                .fillMaxSize()
                                .clickable {
                                    isSelectedRoom = false
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Entire home",
                                color = colorTextHome,
                                fontSize = 17.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    ButtonCustom(
                        title = "Book",
                        greenBackground = true,
                        onClickButton = {
                            navController.navigate("${Screens.DetailScreen.route}/$username/$typeOfTruck/$totalPrice/$typeOfPlace/$km/$location/$destination/$date")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}