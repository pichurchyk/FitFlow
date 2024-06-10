package com.pichurchyk.fitflow.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.theme.AppFont
import com.pichurchyk.fitflow.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorBottomSheet(
    errorMessage: String,
    onDismiss: () -> Unit = {},
    dismissButtonText: String = stringResource(id = R.string.got_it)
) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    val sheetState = rememberModalBottomSheetState(true)

    ModalBottomSheet(
        sheetState = sheetState,
        shape = RoundedCornerShape(8.dp),
        dragHandle = null,
        onDismissRequest = { onDismiss.invoke() },
        windowInsets = WindowInsets(0),
        modifier = Modifier.padding(bottom = bottomPadding)
    ) {
        BottomSheetContent(
            errorMessage = errorMessage,
            okButtonText = dismissButtonText,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun BottomSheetContent(
    errorMessage: String,
    okButtonText: String,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.error)
            .padding(16.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = errorMessage,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onError
        )

        DismissButton(
            text = okButtonText,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun DismissButton(modifier: Modifier = Modifier, text: String, onDismiss: () -> Unit) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onError, RoundedCornerShape(12.dp))
            .padding(6.dp)
            .clickable {
                onDismiss()
            }
    ) {
        Text(
            modifier = modifier,
            text = text,
            fontFamily = AppFont.interFont,
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        BottomSheetContent(
            errorMessage = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lo",
            okButtonText = "Got it",
            onDismiss = {}
        )
    }
}