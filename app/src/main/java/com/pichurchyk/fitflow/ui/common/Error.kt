package com.pichurchyk.fitflow.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Error(
    errorMessage: String,
    onDismiss: () -> Unit = {}
) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    val sheetState = rememberModalBottomSheetState(true)

    ModalBottomSheet(
        sheetState = sheetState,
        shape = RoundedCornerShape(8.dp),
        dragHandle = null,
        onDismissRequest = { onDismiss.invoke() },
        containerColor = MaterialTheme.colorScheme.error,
        windowInsets = WindowInsets(0),
        ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            Text(errorMessage, fontSize = 20.sp, modifier = Modifier.padding(bottom = bottomPadding))
        }

    }
}