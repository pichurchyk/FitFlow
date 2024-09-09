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
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.ui.theme.TextStyles

class SnackbarInfo(
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

    val isError = (data.visuals as? SnackbarInfo)?.isError ?: false

    val messageColor = if (isError) {
        MaterialTheme.colorScheme.onError
    } else {
        SnackbarDefaults.contentColor
    }

    val backgroundColor = if (isError) {
        MaterialTheme.colorScheme.error
    } else {
        SnackbarDefaults.color
    }

    Snackbar(
        modifier = Modifier
            .padding(16.dp),
        containerColor = backgroundColor
    ) {
        Text(
            text = data.visuals.message,
            color = messageColor,
            style = TextStyles.bodySmall
        )
    }
}