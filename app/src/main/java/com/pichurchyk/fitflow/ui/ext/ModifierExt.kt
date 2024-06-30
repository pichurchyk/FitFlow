package com.pichurchyk.fitflow.ui.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController


fun Modifier.thenIf(condition: Boolean, modifier: Modifier): Modifier {
    return if (condition) this.then(modifier) else this
}

@Composable
fun Modifier.clearFocusOnClick(): Modifier {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    return this.clickable(
        interactionSource = interactionSource,
        indication = null
    ) {
        keyboardController?.hide()
        focusManager.clearFocus()

    }
}