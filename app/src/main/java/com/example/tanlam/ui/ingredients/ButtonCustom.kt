package com.example.tanlam.ui.ingredients

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.tanlam.theme.MainGreen

@Composable
fun ButtonCustom(
    title: String,
    greenBackground: Boolean,
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    var colorBackground by remember {
        mutableStateOf(MainGreen)
    }
    var textColor by remember {
        mutableStateOf(Color.White)
    }

    if (greenBackground == false) {
        colorBackground = Color.White
        textColor = MainGreen
    }

    Button(
        onClick = {
            onClickButton()
        },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            colorBackground,
            textColor
        )
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun testButton() {
    ButtonCustom("Get Started", false, onClickButton = {})
}