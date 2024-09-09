package com.pichurchyk.fitflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.pichurchyk.fitflow.ui.nav.NavHost
import com.pichurchyk.fitflow.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                NavHost(rememberNavController())
            }
        }
    }
}