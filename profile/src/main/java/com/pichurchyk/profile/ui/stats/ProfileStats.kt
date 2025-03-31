package com.pichurchyk.profile.ui.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.common.ui.theme.AppTheme
import com.pichurchyk.fitflow.common.ui.theme.TextStyles
import com.pichurchyk.fitflow.common.ui.theme.color_blue
import com.pichurchyk.fitflow.common.ui.theme.color_green
import com.pichurchyk.fitflow.common.ui.theme.color_red
import com.pichurchyk.profile.R
import com.pichurchyk.profile.domain.model.UserParams

@Composable
fun ProfileStats(
    modifier: Modifier = Modifier,
    userParams: UserParams
) {
    Column(
        modifier
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            style = TextStyles.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            text = stringResource(R.string.your_params)
        )

        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ProfileStatsItem(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.ic_ruler_vertical,
                iconTint = color_blue,
                title = R.string.height,
                value = stringResource(R.string.placeholder_cm, userParams.height)
            )

            ProfileStatsItem(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.ic_weight,
                iconTint = color_red,
                title = R.string.weight,
                value = stringResource(R.string.placeholder_kg, userParams.weight)
            )

            ProfileStatsItem(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.ic_age,
                iconTint = color_green,
                title = R.string.age,
                value = userParams.age.toString()
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        ProfileStats(
            userParams = UserParams(
            weight = 72.5,
            height = 175,
            age = 25
            )
        )
    }
}