package com.example.tanlam.nav

sealed class Screens (
    val route: String
){
    object StartScreen: Screens(route = "start")
    object LoginScreen: Screens(route = "login")
    object SignupScreen: Screens(route = "signup")
    object ProfileScreen: Screens(route = "profile")
    object MapScreen: Screens(route = "mapScreen")
    object BookScreen: Screens(route = "bookScreen")
    object DetailScreen: Screens(route = "detail")
    object AdminScreen: Screens(route = "admin")
}