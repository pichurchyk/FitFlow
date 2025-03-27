package com.pichurchyk.profile.ui.stats

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.common.ui.theme.AppTheme
import com.pichurchyk.fitflow.common.ui.theme.TextStyles
import com.pichurchyk.profile.R

@Composable
fun ProfileStatsItem(
    modifier: Modifier,
    @DrawableRes iconRes: Int,
    iconTint: Color,
    @StringRes title: Int,
    value: String,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(6.dp).align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ){
            Icon(
                painter = painterResource(iconRes),
                contentDescription = "",
                tint = iconTint
            )

            Text(
                text = stringResource(title),
                style = TextStyles.labelMedium
            )

            Text(
                text = value,
                style = TextStyles.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        ProfileStatsItem(
            modifier = Modifier,
            iconRes = R.drawable.ic_upload,
            iconTint = Color.Magenta,
            title = R.string.profile,
            value = 100.toString()
        )
    }
}