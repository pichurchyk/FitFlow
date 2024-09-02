package com.pichurchyk.fitflow.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.ext.doOnClick

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Icon(
                modifier = Modifier
                    .doOnClick {
                        onBackPressed()
                    },
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = stringResource(
                    id = R.string.back
                )
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}