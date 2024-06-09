package com.pichurchyk.fitflow.ui.ext

import androidx.compose.ui.Modifier


fun Modifier.thenIf(condition: Boolean, modifier: Modifier): Modifier {
    return if (condition) this.then(modifier) else this
}