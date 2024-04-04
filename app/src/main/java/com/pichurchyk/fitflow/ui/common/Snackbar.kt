package com.pichurchyk.fitflow.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Snackbar(
    override val message: String,
    val actionText: String = "OK",
    val isError: Boolean,
) : SnackbarVisuals {
    override val actionLabel: String
        get() = actionText
    override val withDismissAction: Boolean
        get() = false
    override val duration: SnackbarDuration
        get() = SnackbarDuration.Short
}


@Composable
fun CustomSnackbar(data: SnackbarData) {

    val isError = (data.visuals as? Snackbar)?.isError ?: false

    val messageColor = if (isError) {
        MaterialTheme.colorScheme.error
    } else {
        SnackbarDefaults.contentColor
    }

    Snackbar(
        modifier = Modifier
            .padding(20.dp),
    ) {
        Text(
            data.visuals.message,
            color = messageColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}