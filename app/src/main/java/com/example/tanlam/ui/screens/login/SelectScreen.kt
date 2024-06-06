package com.example.tanlam.ui.screens.login

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tanlam.R
import com.example.tanlam.common.isEmptyString
import com.example.tanlam.controller.viewmodel.DataViewModel
import com.example.tanlam.data.data_app.Account
import com.example.tanlam.nav.Screens
import com.example.tanlam.theme.MainGreen
import com.example.tanlam.ui.ingredients.ButtonCustom
import com.example.tanlam.ui.ingredients.TextFieldCustom

@Composable
fun SelectScreen(
    dataViewModel: DataViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var showLoginScreen by remember { mutableStateOf(false) }
    var listAccount by remember { mutableStateOf(emptyList<Account>()) }
    dataViewModel.getAllAccount {
        listAccount = it
    }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.truckbackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (showLoginScreen) {
                LoginDialog(
                    dataViewModel = dataViewModel,
                    navController = navController
                )
            } else {
                Column {
                    ButtonCustom(
                        title = "SIGN UP",
                        greenBackground = true,
                        onClickButton = {
                            navController.navigate(Screens.SignupScreen.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 90.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    ButtonCustom(
                        title = "LOG IN",
                        greenBackground = false,
                        onClickButton = {
                            showLoginScreen = !showLoginScreen
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 90.dp, end = 90.dp, bottom = 80.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LoginDialog(
    dataViewModel: DataViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var userName by remember { mutableStateOf("") }
    var passWord by remember { mutableStateOf("") }
    var account by remember { mutableStateOf(Account()) }

    var successAccount by remember { mutableStateOf(false) }
    var checkFillAccount by remember { mutableStateOf(false) }
    var isSubmit by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    if (isEmptyString(userName)) {
        error = "invalid username"
        checkFillAccount = false
    } else if (isEmptyString(passWord)) {
        error = "invalid password"
        checkFillAccount = false
    } else {
        checkFillAccount = true

        dataViewModel.retrieveAccount(userName = userName, data = {
            account = it
        })
    }



    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(450.dp)
            .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            .background(Color.White)
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome back!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MainGreen
        )

        Spacer(modifier = Modifier.height(60.dp))

        TextFieldCustom(
            title = "User name",
            isPassWord = false,
            onChangeValue = {
                userName = it
                isSubmit = false
            },
            isNumber = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextFieldCustom(
            title = "Password",
            isPassWord = true,
            onChangeValue = {
                passWord = it
                isSubmit = false
            },
            isNumber = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp)
        )

        if (isSubmit) {
            if(account.passWord == passWord) {
                successAccount = true
            }

            if (checkFillAccount == false) {
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }else if(successAccount == false) {
                Text(
                    text = "error password",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            } else {
                if(account.userName == "admin") {
                    navController.navigate(Screens.AdminScreen.route)
                }else {
                    navController.navigate("${Screens.MapScreen.route}/$userName")
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        ButtonCustom(
            title = "Log in",
            greenBackground = true,
            onClickButton = {
                isSubmit = true
//                if (checkFillAccount) {
//                    if (successAccount) {
//                        navController.navigate("${Screens.MapScreen.route}/$userName")
//                    }
//                }
            },
            modifier = Modifier.width(120.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .clickable {
                    navController.navigate(Screens.SignupScreen.route)
                },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Need an account? ")
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Sign up",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MainGreen
            )
        }
    }
}