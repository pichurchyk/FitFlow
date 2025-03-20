package com.pichurchyk.profile.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
) {
    BackHandler {
        onBackPressed()
    }

    Column(Modifier.fillMaxSize().background(Color.Red)) {  }
}