package com.pichurchyk.profile.ui.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.common.ui.theme.AppTheme
import com.pichurchyk.fitflow.common.ui.theme.color_blue
import com.pichurchyk.fitflow.common.ui.theme.color_green
import com.pichurchyk.fitflow.common.ui.theme.color_red
import com.pichurchyk.profile.R

@Composable
fun ProfileStats(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ProfileStatsItem(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.ic_ruler_vertical,
            iconTint = color_blue,
            title = R.string.height,
            value = stringResource(R.string.placeholder_cm, "175")
        )

        ProfileStatsItem(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.ic_weight,
            iconTint = color_red,
            title = R.string.weight,
            value = stringResource(R.string.placeholder_kg, "75")
        )

        ProfileStatsItem(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.ic_age,
            iconTint = color_green,
            title = R.string.age,
            value = "25"
        )
    }
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        ProfileStats()
    }
}