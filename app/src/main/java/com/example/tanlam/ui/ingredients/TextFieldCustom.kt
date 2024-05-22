package com.example.tanlam.ui.ingredients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TextFieldCustom(
    title: String,
    isPassWord: Boolean,
    onChangeValue: (String) -> Unit,
    isNumber: Boolean,
    conTentCenter: Boolean? = null,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    var typeKeyBoard by remember { mutableStateOf(KeyboardType.Text) }
    var visualTransformation by remember { mutableStateOf(VisualTransformation.None) }
    var keyBoardOption by remember { mutableStateOf(KeyboardType.Text) }
    var paddingStart by remember { mutableStateOf(0.dp) }
    val keyboardController =
        LocalSoftwareKeyboardController.current // Bien de ta truy cap vao ban phim dien thoai

    if (isPassWord) {
        visualTransformation = PasswordVisualTransformation()
    }

    if(isNumber) {
        keyBoardOption = KeyboardType.Number
    }

    if (conTentCenter == null) {
        paddingStart = 20.dp
    }

    Box(
        modifier = modifier
            .background(
                Color(237, 237, 237),
                shape = RoundedCornerShape(30.dp)
            )
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {

            BasicTextField(
                value = text,
                onValueChange = { newValue ->
                    text = newValue
                    onChangeValue(text)
                },
                cursorBrush = SolidColor(Color.Black),
                decorationBox = { innerTextField ->
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Box(
                        ) {
                            if (text.isEmpty()) {
                                Text(
                                    text = title,
                                    style = LocalTextStyle.current.copy(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                        fontSize = 15.sp
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = visualTransformation,
                keyboardOptions = KeyboardOptions(keyboardType = keyBoardOption)
            )
    }
}