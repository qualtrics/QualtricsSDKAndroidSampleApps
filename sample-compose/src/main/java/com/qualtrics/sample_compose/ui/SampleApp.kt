package com.qualtrics.sample_compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    var lastDestination by rememberSaveable { mutableStateOf("") }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        val newDestination = destination.route ?: "Main"
        if (newDestination != lastDestination) {
            // This block is executed only when the navigation destination changes.
            // It avoids unnecessary calls to onPageChange during configuration changes such as screen rotations.
            onPageChange(newDestination)
            lastDestination = newDestination
        }
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