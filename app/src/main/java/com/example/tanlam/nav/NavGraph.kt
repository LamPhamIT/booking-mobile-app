package com.example.tanlam.nav

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tanlam.controller.viewmodel.DataViewModel
import com.example.tanlam.controller.viewmodel.NotificationModel
import com.example.tanlam.data.data_app.Book
import com.example.tanlam.ui.screens.admin.AdminScreen
import com.example.tanlam.ui.screens.login.SelectScreen
import com.example.tanlam.ui.screens.login.SignupScreen
import com.example.tanlam.ui.screens.login.StartScreen
import com.example.tanlam.ui.screens.main.BookScreen
import com.example.tanlam.ui.screens.main.DetailScreen
import com.example.tanlam.ui.screens.main.MapScreen
import com.example.tanlam.ui.screens.main.ProfileScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    dataViewModel: DataViewModel,
    notificationModel: NotificationModel,
    context: Context,
    paddingValues: PaddingValues,
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screens.StartScreen.route) {
        composable(route = Screens.StartScreen.route) {
            StartScreen(navController = navController)
        }

        composable(route = Screens.LoginScreen.route) {
            SelectScreen(
                dataViewModel = dataViewModel,
                navController = navController
            )
        }

        composable(route = Screens.SignupScreen.route) {
            SignupScreen(
                navController = navController,
                dataViewModel = dataViewModel
            )
        }

        composable(
            route = "${Screens.ProfileScreen.route}/{userName}",
            arguments = listOf(
                navArgument(name = "userName") {
                    type = NavType.StringType
                }
            )
        ) {
            ProfileScreen(
                paddingValues = paddingValues,
                userName = it.arguments?.getString("userName").toString(),
                dataViewModel = dataViewModel,
                notificationModel = notificationModel,
                navController = navController
            )
        }

        composable(
            route = "${Screens.MapScreen.route}/{userName}",
            arguments = listOf(
                navArgument(name = "userName") {
                    type = NavType.StringType
                }
            )
        ) {
            MapScreen(
                userName = it.arguments?.getString("userName").toString(),
                notificationModel = notificationModel,
                navController = navController
            )
        }

        composable(
            route = "${Screens.BookScreen.route}/{username}/{location}/{destination}/{date}",
            arguments = listOf(
                navArgument(name = "username") {
                    type = NavType.StringType
                },
                navArgument(name = "location") {
                    type = NavType.StringType
                },
                navArgument(name = "destination") {
                    type = NavType.StringType
                },
                navArgument(name = "date") {
                    type = NavType.StringType
                }
            )
        ) {
            val username = it.arguments?.getString("username").toString()
            val location = it.arguments?.getString("location").toString()
            val destination = it.arguments?.getString("destination").toString()
            val date = it.arguments?.getString("date").toString()

            BookScreen(
                paddingValues = paddingValues,
                username = username,
                location = location,
                destination = destination,
                date = date,
                notificationModel = notificationModel,
                navController = navController
            )
        }

        composable(
            route = "${Screens.DetailScreen.route}/{username}/{nameTruck}/{price}/{typeRoom}/{km}/{location}/{destination}/{date}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                },
                navArgument("nameTruck") {
                    type = NavType.StringType
                },
                navArgument("price") {
                    type = NavType.StringType
                },
                navArgument("typeRoom") {
                    type = NavType.StringType
                },
                navArgument("km") {
                    type = NavType.StringType
                },
                navArgument("location") {
                    type = NavType.StringType
                },
                navArgument("destination") {
                    type = NavType.StringType
                },
                navArgument("date") {
                    type = NavType.StringType
                }
            )
        ) {
            val username = it.arguments?.getString("username").toString()
            val truck = it.arguments?.getString("nameTruck").toString()
            val price = it.arguments?.getString("price").toString()
            val typeRoom = it.arguments?.getString("typeRoom").toString()
            val km = it.arguments?.getString("km").toString()
            val location = it.arguments?.getString("location").toString()
            val destination = it.arguments?.getString("destination").toString()
            val date = it.arguments?.getString("date").toString()

            val book = Book(
                username = username,
                location = location,
                destination = destination,
                date = date,
                nameTruck = truck,
                price = price,
                typeRoom = typeRoom,
                km = km
            )

            DetailScreen(
                paddingValues = paddingValues,
                book = book,
                notificationModel = notificationModel,
                dataViewModel = dataViewModel,
                navController = navController
            )
        }

        composable(route = Screens.AdminScreen.route) {
            AdminScreen(
                dataViewModel = dataViewModel,
                notificationModel = notificationModel,
                context = context,
                paddingValues = paddingValues
            )
        }
    }
}