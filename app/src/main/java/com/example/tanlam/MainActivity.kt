package com.example.tanlam

import android.app.Notification
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tanlam.controller.viewmodel.DataViewModel
import com.example.tanlam.controller.viewmodel.NotificationModel
import com.example.tanlam.nav.NavGraph
import com.example.tanlam.theme.TanLamTheme

private lateinit var notification: Notification

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val dataViewModel: DataViewModel by viewModels()
    private val notificationModel: NotificationModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            val context = LocalContext.current
            TanLamTheme {
                Scaffold {
                    NavGraph(
                        dataViewModel = dataViewModel,
                        notificationModel = notificationModel,
                        context = context,
                        paddingValues = it,
                        navController = navController
                    )
                }
            }
        }
    }
}