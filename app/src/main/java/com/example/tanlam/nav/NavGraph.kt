package com.example.tanlam.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tanlam.controller.viewmodel.DataViewModel
import com.example.tanlam.data.Book
import com.example.tanlam.ui.screens.login.SelectScreen
import com.example.tanlam.ui.screens.login.SignupScreen
import com.example.tanlam.ui.screens.login.StartScreen
import com.example.tanlam.ui.screens.main.BookScreen
import com.example.tanlam.ui.screens.main.DetailScreen
import com.example.tanlam.ui.screens.main.MapScreen
import com.example.tanlam.ui.screens.main.ProfileScreen

@Composable
fun NavGraph(
    dataViewModel: DataViewModel,
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
                userName = it.arguments?.getString("userName").toString(),
                dataViewModel = dataViewModel,
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
                navController = navController
            )
        }

        composable(route = Screens.BookScreen.route) {
            BookScreen(navController = navController)
        }

        composable(
            route = "${Screens.DetailScreen.route}/{nameTruck}/{price}/{typeRoom}/{km}",
            arguments = listOf(
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
                }
            )
        ) {
            val truck = it.arguments?.getString("nameTruck").toString()
            val price = it.arguments?.getString("price").toString()
            val typeRoom = it.arguments?.getString("typeRoom").toString()
            val km = it.arguments?.getString("km").toString()

            val book = Book(
                truck,
                price,
                typeRoom,
                km
            )

            DetailScreen(
                book = book,
                dataViewModel = dataViewModel,
                navController = navController
            )
        }
    }
}