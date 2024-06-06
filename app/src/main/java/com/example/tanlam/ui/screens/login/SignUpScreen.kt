package com.example.tanlam.ui.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tanlam.R
import com.example.tanlam.common.isEmptyString
import com.example.tanlam.common.isValidDayOfBirth
import com.example.tanlam.common.isValidEmail
import com.example.tanlam.controller.collection.Collections
import com.example.tanlam.controller.viewmodel.DataViewModel
import com.example.tanlam.data.data_app.Account
import com.example.tanlam.nav.Screens
import com.example.tanlam.theme.MainGreen
import com.example.tanlam.ui.ingredients.ButtonCustom
import com.example.tanlam.ui.ingredients.TextFieldCustom


@Composable
fun SignupScreen(
    dataViewModel: DataViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var disPlayName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var dayOfBirth by remember { mutableStateOf("") }
    var passWord by remember { mutableStateOf("") }

    var month by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    dayOfBirth = "$day-$month-$year"

    var error by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    var account by remember { mutableStateOf(Account()) }
    account = Account(
        userName,
        disPlayName,
        passWord,
        dayOfBirth,
        email
    )

    if (!isValidEmail(email)) {
        error = "invalid email"
    } else if (isEmptyString(userName) || isEmptyString(disPlayName)) {
        error = "invalid name"
    } else if (!isValidDayOfBirth(dayOfBirth)) {
        error = "Error day of birth"
    } else if (isEmptyString(passWord)) {
        error = "invalid password"
    }else {
        error = ""
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .border(
                        BorderStroke(2.dp, Color(171, 170, 165)),
                        shape = RoundedCornerShape(40.dp)
                    )
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color(235, 235, 233))
                    .clickable {
                        navController.popBackStack()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Create an Account",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MainGreen,
                modifier = Modifier.width(210.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TextFieldCustom(
                title = "Email",
                isPassWord = false,
                onChangeValue = {
                    email = it
                },
                isNumber = false,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextFieldCustom(
                    title = "Display Name",
                    isPassWord = false,
                    onChangeValue = {
                        disPlayName = it
                    },
                    isNumber = false,
                    modifier = Modifier.weight(4f)
                )

                Box(modifier = Modifier.weight(0.5f))

                TextFieldCustom(
                    title = "Username",
                    isPassWord = false,
                    onChangeValue = {
                        userName = it
                    },
                    isNumber = false,
                    modifier = Modifier.weight(4f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextFieldCustom(
                    title = "Month",
                    isPassWord = false,
                    onChangeValue = {
                        month = it
                    },
                    isNumber = true,
                    modifier = Modifier.weight(4f)
                )

                Box(modifier = Modifier.weight(0.3f))

                TextFieldCustom(
                    title = "Day",
                    isPassWord = false,
                    onChangeValue = {
                        day = it
                    },
                    isNumber = true,
                    modifier = Modifier.weight(4f)
                )

                Box(modifier = Modifier.weight(0.3f))

                TextFieldCustom(
                    title = "Year",
                    isPassWord = false,
                    onChangeValue = {
                        year = it
                    },
                    isNumber = true,
                    modifier = Modifier.weight(4f)
                )
            }

            TextFieldCustom(
                title = "Password",
                isPassWord = true,
                onChangeValue = {
                    passWord = it
                },
                isNumber = false,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (showError) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 10.dp)
        ) {
            ButtonCustom(
                title = "Sign up",
                greenBackground = true,
                onClickButton = {
                    if (error == "") {
                        dataViewModel.addData(
                            account,
                            Collections.Account.name,
                            account.userName
                        )

                        navController.navigate(Screens.LoginScreen.route)
                    } else {
                        showError = true
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    navController.navigate(Screens.LoginScreen.route)
                }
            ) {
                Text(text = "Already have an account?")
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Log in",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        val text = buildAnnotatedString {
            append("By registering, you agree to ChatterBox’s ")
            withStyle(style = SpanStyle(color = MainGreen, fontWeight = FontWeight.Bold)) {
                append("Terms of Service")
            }
            append(" and ")
            withStyle(style = SpanStyle(color = MainGreen, fontWeight = FontWeight.Bold)) {
                append("Privacy Policy")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier)
            Text(
                text = text,
                textAlign = TextAlign.Center,
            )
        }
    }
}