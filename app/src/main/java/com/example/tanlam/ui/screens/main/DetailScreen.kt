package com.example.tanlam.ui.screens.main

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tanlam.R
import com.example.tanlam.ui.ingredients.ButtonCustom
import com.example.tanlam.ui.ingredients.TextFieldCustom

@Composable
fun DetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(237, 237, 235))
            .padding(top = 40.dp)
    ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 30.dp),
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
            // Adress
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
                    Text(text = "37 Yersin St.", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = "37 Yersin St., Khue My, Ngu Hanh Son, Da Nang", fontSize = 10.sp)
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
                    Text(text = "Small truck (1000kg - 5000kg)", fontSize = 12.sp)
                }
            }


            var isPayLater by remember { mutableStateOf(true) }
            var payLaterIcon by remember { mutableStateOf(0) }
            var payVNPAY by remember { mutableStateOf(0) }

            if (isPayLater == false) {
                payLaterIcon = R.drawable.unselected_icon
                payVNPAY = R.drawable.selectedicon
            }else {
                payLaterIcon = R.drawable.selectedicon
                payVNPAY = R.drawable.unselected_icon
            }
            //Payment
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
                        Text(text = "5Km", fontWeight = FontWeight.ExtraBold)
                    }

                    Text(text = "300.000đ", fontWeight = FontWeight.ExtraBold)
                }
            }
        }

        //User information
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

                    },
                    isNumber = true,
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total price")
                    Text(
                        text = "300.000đ",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 19.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                ButtonCustom(
                    title = "Place your order",
                    greenBackground = true,
                    modifier = Modifier.fillMaxWidth(),
                    onClickButton = { /*TODO*/ }
                )
            }
        }
    }
}