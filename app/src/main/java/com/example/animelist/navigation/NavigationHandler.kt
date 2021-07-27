package com.example.animelist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.animelist.screen.HomeScreen

@Composable
fun NavigationHandler() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenList.HomeScreen.route) {
        composable(
            route = ScreenList.HomeScreen.route
        ){
            /*Add home page file route here*/
            HomeScreen(navController = navController)
        }
        composable(
            route = ScreenList.DetailScreen.route
                    + "/anime"
                    + "dominantColor={dominantColor}&?name={name}",
            arguments =listOf(
                navArgument("dominantColor") {
                    type = NavType.IntType
                },
                navArgument("anime_name"){
                    type = NavType.StringType
                    defaultValue = "No Data Found"
                    nullable = true
                },
            )
        ){
//            entry ->
//            val anime_name = entry.arguments?.getString("name")
//            val anime_desc = entry.arguments?.getString("desc")
//            val anime_director = entry.arguments?.getString("director")
//            val anime_producer = entry.arguments?.getString("producer")
//            val anime_rate = entry.arguments?.getInt("rate")

            /*Here add the Detail Page Route to passing the data*/
        }
    }
}