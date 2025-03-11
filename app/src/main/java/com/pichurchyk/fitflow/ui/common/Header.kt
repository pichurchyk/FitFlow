package com.pichurchyk.fitflow.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
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
import com.pichurchyk.fitflow.ui.theme.TextStyles

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: (() -> Unit)?  = null
) {
    Box(
        modifier = modifier
            .padding(top = 16.dp, bottom = 12.dp, end = 16.dp, start = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        onBackPressed?.let {
            Box(contentAlignment = Alignment.CenterStart) {
                Icon(
                    modifier = Modifier
                        .doOnClick {
                            onBackPressed()
                        }
                        .size(22.dp),
                    imageVector = Icons.Rounded.Close,
                    contentDescription = stringResource(
                        id = R.string.back
                    )
                )
            }
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = TextStyles.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}