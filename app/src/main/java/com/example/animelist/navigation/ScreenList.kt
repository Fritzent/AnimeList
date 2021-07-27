package com.example.animelist.navigation

sealed class ScreenList(val route: String) {
    object HomeScreen: ScreenList("home_screen")
    object DetailScreen: ScreenList("detail_screen")
}