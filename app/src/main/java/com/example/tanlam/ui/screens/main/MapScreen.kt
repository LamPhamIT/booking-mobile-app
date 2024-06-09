package com.example.tanlam.ui.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tanlam.R
import com.example.tanlam.ShareLiveDataModel
import com.example.tanlam.common.isEmptyString
import com.example.tanlam.controller.notification.NotificationId
import com.example.tanlam.controller.notification.showNotification
import com.example.tanlam.controller.viewmodel.NotificationModel
import com.example.tanlam.data.notification.Notification
import com.example.tanlam.nav.Screens
import com.example.tanlam.ui.ingredients.ButtonCustom
import com.example.tanlam.ui.ingredients.MapUI
import com.example.tanlam.ui.ingredients.getMapLocation
import com.example.tanlam.ui.ingredients.rememberMapViewWithLifecycle
import com.google.android.gms.maps.MapView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    userName: String,
    notificationModel: NotificationModel,
    navController: NavController,
    shareLiveDataModel: ShareLiveDataModel = viewModel()
) {
    var showSearchDialog by remember { mutableStateOf(false) }

    var location by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    val mapView = rememberMapViewWithLifecycle()

    //Day
    var day by remember { mutableStateOf("") }
    val calendarState = rememberSheetState()

    val context = LocalContext.current

    var error by remember { mutableStateOf("") }
    var showIncorrectDialog by remember { mutableStateOf(false) }


    //Notification
    var notification by remember { mutableStateOf(Notification()) }
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference(NotificationId.NOTIFICATION_CHECK).child(userName)
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
        notificationModel.removeNotificationForUser(userName)

        showNotification(
            context = context,
            title = "Notification from Admin!!!!",
            content = notification.content
        )
    }

    //Map
    var isFindMapByClick by remember { mutableStateOf(false) }
    var location_or_destination by remember { mutableStateOf(0) }

    val addressAfterFind by shareLiveDataModel.inputData.observeAsState(initial = "")


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        MapUI(
            mapView = mapView,
            context = context,
            clickToFindAddress = isFindMapByClick,
            shareLiveDataModel = shareLiveDataModel
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(9f)
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(30.dp)
                            )
                            .clickable {
                                showSearchDialog = true
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                top = 10.dp,
                                end = 10.dp,
                                bottom = 10.dp,
                                start = 20.dp
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.search_icon),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Where to?",
                                    fontSize = 17.sp
                                )
                                Text(
                                    text = "Anywhere - Everyday - Everytime",
                                    fontSize = 10.sp,
                                    color = Color(169, 169, 169)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .height(50.dp)
                            .width(50.dp)
                            .clickable {
                                navController.navigate("${Screens.ProfileScreen.route}/$userName")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(10.dp)
                                .size(30.dp)
                        )
                    }
                }

                if (isFindMapByClick) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = addressAfterFind,
                                modifier = Modifier.weight(8f),
                                maxLines = 1
                            )
                            Box(
                                modifier = Modifier
                                    .weight(0.1f)
                                    .background(Color.Gray)
                            )
                            TextButton(
                                onClick = {
                                    if(location_or_destination == 1) {
                                        location = addressAfterFind
                                    }else if(location_or_destination == 2) {
                                        destination = addressAfterFind
                                    }
                                    showSearchDialog = true
                                },
                                modifier = Modifier.weight(2f)
                            ) {
                                Text(
                                    text = "OK",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Column {
                ButtonCustom(
                    title = "Select your day",
                    greenBackground = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    onClickButton = {
                        calendarState.show()
                    }
                )

                ButtonCustom(
                    title = "Check your selection",
                    greenBackground = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 30.dp,
                            vertical = 20.dp
                        ),
                    onClickButton = {
                        if (isEmptyString(location) || isEmptyString(destination)) {
                            error = "invalid location"
                            showIncorrectDialog = true
                        } else if (isEmptyString(day)) {
                            error = "invalid date"
                            showIncorrectDialog = true
                        } else {
                            navController.navigate("${Screens.BookScreen.route}/$userName/$location/$destination/$day")
                        }
                    }
                )
            }
        }
    }

    if (showSearchDialog) {
        SearchPlaceDialog(
            mapView = mapView,
            onDismissRequest = {
                showSearchDialog = false

                if(it != 0) {
                    isFindMapByClick = true
                    location_or_destination = it
                }else {
                    isFindMapByClick = false
                }
            },
            onChangeValue = { locationText, destinationText ->
                location = locationText
                destination = destinationText
            },
            location = location,
            destination = destination
        )
    }

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date { date ->
            if(date.isBefore(LocalDate.now())) {
                error = "error day"
                showIncorrectDialog = true
            }else {
                day = "$date"
            }
        }
    )

    if(showIncorrectDialog) {
        InCorrectDialog(
            error = error,
            onDismissRequest = {
                showIncorrectDialog = false
            }
        )
    }
}

@Composable
fun SearchPlaceDialog(
    mapView: MapView,
    location: String,
    destination: String,
    onDismissRequest: (Int) -> Unit,
    onChangeValue: (String, String) -> Unit
) {
    var locationTest by remember { mutableStateOf(location) }
    var destinationTest by remember { mutableStateOf(destination) }
    val context = LocalContext.current

    Dialog(
        onDismissRequest = {
            onDismissRequest(0)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Where?",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                modifier = Modifier.weight(7f),
                                value = locationTest,
                                onValueChange = {
                                    locationTest = it
                                },
                                label = {
                                    Text(text = "Your location")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                ),
                                maxLines = 1,
                                singleLine = true,
                            )

                            TextButton(
                                onClick = {
                                    onDismissRequest(1)
                                },
                                modifier = Modifier.weight(3f)
                            ) {
                                Text(text = "select")
                            }
                        }

                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            OutlinedTextField(
                                modifier = Modifier.weight(7f),
                                value = destinationTest,
                                onValueChange = {
                                    destinationTest = it
                                },
                                label = {
                                    Text(text = "Your destination")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                ),
                                maxLines = 1,
                                singleLine = true,
                            )

                            TextButton(
                                onClick = {
                                    onDismissRequest(2)
                                },
                                modifier = Modifier.weight(3f)
                            ) {
                                Text(text = "select")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                ButtonCustom(
                    title = "Search",
                    greenBackground = true,
                    onClickButton = {
                        onChangeValue(locationTest, destinationTest)
                        onDismissRequest(0)

                        getMapLocation(
                            locationTest,
                            destinationTest,
                            context = context,
                            mapView = mapView,
                            getKm = {}
                        )
                    },
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
fun InCorrectDialog(
    error: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = modifier
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Image(
                painter = painterResource(id = R.drawable.incorrect),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun test() {
//    SearchPlaceDialog()
}