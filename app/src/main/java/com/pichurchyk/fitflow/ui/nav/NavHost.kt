package com.pichurchyk.fitflow.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.pichurchyk.fitflow.ui.screen.auth.AuthScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pichurchyk.fitflow.ui.ext.navigateSingleTopTo
import com.pichurchyk.fitflow.ui.screen.addintake.AddIntakeScreen
import com.pichurchyk.fitflow.ui.screen.dashboard.DashboardScreen
import java.time.Instant
import java.util.Date

@Composable
fun NavHost(
    navController: NavHostController
) {
    NavHost(navController, startDestination = Screen.Auth) {
        composable<Screen.Auth> {
            AuthScreen(
                openDashboard = {
                    navController.navigateSingleTopTo(Screen.Dashboard)
                }
            )
        }

        composable<Screen.Dashboard> {
            DashboardScreen()
        }

        composable<Screen.AddIntake> {backStackEntry ->
            val selectedDate = backStackEntry.toRoute<Screen.AddIntake>().selectedDateMillis.let {
                Date.from(Instant.ofEpochMilli(it))
            }

            AddIntakeScreen(
                selectedDate = selectedDate,
                closeScreen = {
                    navController.popBackStack()
                }
            )
        }
    }
}