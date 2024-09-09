package com.pichurchyk.fitflow.ui.screen.dashboard.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.ext.getColor
import com.pichurchyk.fitflow.ui.theme.AppTheme
import com.pichurchyk.fitflow.ui.theme.color_water
import com.pichurchyk.nutrition.database.model.IntakeType

@Composable
fun WaterBlock(
    modifier: Modifier = Modifier,
    total: Int,
) {
    DashboardItemWrapper(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 40.dp),
        type = DashboardItemWrapperType.FULL,
        title = stringResource(id = R.string.water),
        subtitle = stringResource(id = R.string.total),
        mainText = stringResource(id = R.string.ml_value, total),
        needBottomRadius = true,
        content = {
            AnimatedProgressIndicator(
                modifier = Modifier,
                color = IntakeType.WATER.getColor(),
                value = total,
                type = AnimatedProgressIndicatorType.RADIAL,
                limit = 100
            )
        }, backgroundColor = color_water.copy(alpha = 0.2f)
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun Preview() {
    AppTheme {
        WaterBlock(
            total = 1000
        )
    }
}