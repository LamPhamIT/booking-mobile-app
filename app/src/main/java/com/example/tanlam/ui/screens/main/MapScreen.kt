package com.example.tanlam.ui.screens.main

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
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tanlam.R
import com.example.tanlam.mapUI
import com.example.tanlam.nav.Screens
import com.example.tanlam.ui.ingredients.ButtonCustom

@Composable
fun MapScreen(
    userName: String,
    navController: NavController
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        mapUI(context = context)

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(9f)
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(30.dp)
                        )
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
                                text = "Anywhere - Everyday - conchotanlam",
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

            ButtonCustom(
                title = "Pick your location",
                greenBackground = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 30.dp,
                        vertical = 20.dp
                    ),
                onClickButton = {
                    navController.navigate(Screens.BookScreen.route)
                }
            )
        }
    }
}