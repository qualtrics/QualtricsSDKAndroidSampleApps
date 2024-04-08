package com.qualtrics.sample_compose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun SampleApp(
    onPageChange: (String) -> Unit = {},
) {
    val navController = rememberNavController()
    // This example is using NavHostController from Jetpack Compose Navigation.
    // You can use different approach in a similar manner
    SampleAppNavHost(
        navController = navController,
        onPageChange = onPageChange,
    )
}

@Composable
fun SampleAppNavHost(
    navController: NavHostController,
    onPageChange: (String) -> Unit = {},
) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        onPageChange(destination.route ?: "Main")
    }
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                onSecondaryScreenClicked = {
                    navController.navigate("secondary")
                },
            )
        }
        composable("secondary") {
            SecondaryScreen(onUpClick = {
                navController.navigateUp()
            })
        }
    }
}