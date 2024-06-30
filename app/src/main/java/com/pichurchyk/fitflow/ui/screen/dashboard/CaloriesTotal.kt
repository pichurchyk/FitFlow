package com.pichurchyk.fitflow.ui.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.theme.AppTheme

@Composable
fun CaloriesTotal(
    modifier: Modifier = Modifier,
    value: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        Text(
            text = stringResource(id = R.string.total),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
        Text(
            text = value.toString(),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 50.sp
        )
        Text(
            text = stringResource(id = R.string.kcal),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun Preview() {
    AppTheme {
        CaloriesTotal(
            value = 123
        )
    }
}