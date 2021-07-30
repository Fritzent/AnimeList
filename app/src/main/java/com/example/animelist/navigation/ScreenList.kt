package com.example.animelist.navigation

sealed class ScreenList(val route: String) {
    object LoginScreen: ScreenList("login_screen")
    object RegisterScreen: ScreenList("register_screen")
    object HomeScreen: ScreenList("home_screen")
    object DetailScreen: ScreenList("detail_screen")
}