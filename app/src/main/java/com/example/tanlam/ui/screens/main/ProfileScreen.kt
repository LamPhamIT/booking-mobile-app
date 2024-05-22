@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tanlam.ui.screens.main

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.tanlam.R
import com.example.tanlam.controller.collection.Collections
import com.example.tanlam.controller.viewmodel.DataViewModel
import com.example.tanlam.data.Account
import com.example.tanlam.nav.Screens
import com.example.tanlam.ui.ingredients.ButtonCustom
import com.example.tanlam.ui.ingredients.TextFieldCustom

@Composable
fun ProfileScreen(
    userName: String,
    dataViewModel: DataViewModel,
    navController: NavController
) {

    //Account
    var account by remember { mutableStateOf(Account()) }
    dataViewModel.retrieveAccount(
        userName = userName,
        data = {
            account = it
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(237, 237, 235))
            .padding(top = 40.dp, start = 20.dp, end = 20.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
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
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Profile",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ProfileBox(
                account = account,
                dataViewModel = dataViewModel
            )
            Note()
            ChangePass(
                account = account,
                dataViewModel = dataViewModel
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    navController.navigate(Screens.LoginScreen.route)
                }
            ) {
                Image(Icons.Default.ExitToApp, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Log out",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileBox(
    account: Account,
    dataViewModel: DataViewModel,
    modifier: Modifier = Modifier
) {
    //Image
    val context = LocalContext.current
    var accountTest by remember { mutableStateOf(Account()) }
    var imgUri by remember { mutableStateOf("") }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val laucher = rememberLauncherForActivityResult( // lay anh tu thu vien
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        val let = uri?.let {
            bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }

    accountTest = Account(
        userName = account.userName,
        disPlayName = account.disPlayName,
        passWord = account.passWord,
        dayOfBirth = account.dayOfBirth,
        email = account.email,
        image = imgUri
    )

    Column(
        modifier = modifier
            .shadow(15.dp, shape = RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(horizontal = 40.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        if (bitmap == null) {
            if (account.image == "") {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Blue)
                        .size(90.dp)
                        .border(
                            width = 2.dp, color = Color(217, 217, 217), shape = CircleShape
                        )
                        .clickable {
                            laucher.launch("image/*")
                        }
                )
            } else {
                Image(
                    painter = rememberImagePainter(
                        request = ImageRequest.Builder(LocalContext.current)
                            .data(account.image)
                            .apply {
                                placeholder(R.drawable.baseline_image_24) // Placeholder trong khi tải
                                crossfade(true) // Hiệu ứng chuyển đổi mượt
                            }
                            .build()
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Blue)
                        .size(90.dp)
                        .border(
                            width = 2.dp, color = Color(217, 217, 217), shape = CircleShape
                        )
                        .clickable {
                            laucher.launch("image/*")
                        }
                )
            }
        } else {
            Image(
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Blue)
                    .size(90.dp)
                    .border(
                        width = 2.dp, color = Color(217, 217, 217), shape = CircleShape
                    )
                    .clickable {
                        laucher.launch("image/*")
                    }
            )

            dataViewModel.uploadImageAccount(
                userName = account.userName,
                bitmap = bitmap!!,
            ) { success, imageUrl ->
                if (success) {
                    imageUrl.let {
                        imgUri = it
                    }
                }
            }

            dataViewModel.addData(
                accountTest,
                collection = Collections.Account.name,
                document = account.userName
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            TitleAndValue(title = "Display name", value = account.disPlayName)
            TitleAndValue(title = "User name", value = account.userName)
            TitleAndValue(title = "Email", value = account.email)
            TitleAndValue(title = "Day of birth", value = account.dayOfBirth)
        }
    }
}

@Composable
fun Note(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .shadow(15.dp, shape = RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(horizontal = 40.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(6f)
        ) {
            Text(text = "Delivery your package", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = "It’s simple to get set up \n" + "and book a truck", fontSize = 12.sp
            )
        }
        Image(
            painter = painterResource(id = R.drawable.startimage),
            contentDescription = null,
            modifier = Modifier.weight(4f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePass(
    account: Account,
    dataViewModel: DataViewModel,
    modifier: Modifier = Modifier
) {
    var currentPass by remember { mutableStateOf("") }
    var newPass by remember { mutableStateOf("") }
    var accountTest by remember { mutableStateOf(Account()) }
    var checkPassword by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    if (account.passWord == currentPass) {
        checkPassword = true
    } else {
        checkPassword = false
    }

    accountTest = Account(
        userName = account.userName,
        disPlayName = account.disPlayName,
        passWord = newPass,
        dayOfBirth = account.dayOfBirth,
        email = account.email,
        image = account.image
    )

    Column(
        modifier = modifier
            .shadow(15.dp, shape = RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(horizontal = 40.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp, top = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.key),
                contentDescription = null,
                modifier = Modifier.size(15.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(text = "Change password", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextFieldCustom(
                title = "Current password",
                isPassWord = true,
                isNumber = false,
                onChangeValue = {
                    currentPass = it
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextFieldCustom(
                title = "New password",
                isPassWord = true,
                isNumber = false,
                onChangeValue = {
                    newPass = it
                }
            )
            if (showError) {
                Text(
                    text = "Error change pass",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }

        ButtonCustom(title = "Confirm", greenBackground = true, onClickButton = {
            if (checkPassword == true) {
                if (newPass != "" && newPass != currentPass) {
                    dataViewModel.addData(
                        accountTest,
                        collection = Collections.Account.name,
                        document = account.userName
                    )
                    showError = false
                } else {
                    showError = true
                }
            } else {
                showError = true
            }
        })
    }
}

@Composable
fun TitleAndValue(title: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = title, color = Color(169, 169, 169), fontSize = 8.sp)
        Text(text = value, fontSize = 14.sp)
    }
}