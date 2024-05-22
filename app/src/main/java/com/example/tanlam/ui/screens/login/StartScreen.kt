package com.example.tanlam.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tanlam.R
import com.example.tanlam.nav.Screens
import com.example.tanlam.theme.MainGreen
import com.example.tanlam.ui.ingredients.ButtonCustom

@Composable
fun StartScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MainGreen)
            .padding(vertical = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to...",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Image(
            painter = painterResource(id = R.drawable.startimage),
            contentDescription = null,
            modifier = Modifier.size(450.dp)
        )

        Text(
            text = "A place where you can book a truck to transport the items you need from one place to another",
            fontSize = 15.sp,
            fontWeight = FontWeight.W300,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(50.dp))

        ButtonCustom(
            title = "Get Started",
            greenBackground = false,
            onClickButton = {
                navController.navigate(Screens.LoginScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 80.dp)
                .shadow(9.dp, shape = RoundedCornerShape(100.dp))
        )
    }
}
