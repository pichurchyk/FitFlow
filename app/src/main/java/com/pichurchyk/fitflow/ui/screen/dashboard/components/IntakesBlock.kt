package com.pichurchyk.fitflow.ui.screen.dashboard.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.ext.getTitle
import com.pichurchyk.fitflow.ui.ext.getUnitWithValue
import com.pichurchyk.fitflow.ui.ext.groupByIntakeType
import com.pichurchyk.fitflow.ui.theme.AppTheme
import com.pichurchyk.nutrition.model.Intake

@Composable
fun IntakesBlock(
    modifier: Modifier = Modifier,
    intakes: List<Intake>,
) {
    val items = intakes.groupByIntakeType()

    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(items) { index, combinedIntakes ->
            val isFirstElement = index == 0
            val isLastElement = index == items.size - 1

            val intakesSum = combinedIntakes.intakes.sumOf { it.value }

            DashboardItemWrapper(
                type = DashboardItemWrapperType.FULL,
                title = if (isFirstElement) stringResource(id = R.string.intakes) else null,
                subtitle = stringResource(combinedIntakes.intakeType.getTitle()),
                needBottomRadius = isLastElement,
                mainText = stringResource(id = combinedIntakes.intakeType.getUnitWithValue(), intakesSum),
                content = {
                    AnimatedProgressIndicator(
                        modifier = Modifier,
                        value = intakesSum,
                        type = AnimatedProgressIndicatorType.RADIAL,
                        color = MaterialTheme.colorScheme.primary,
                        limit = 2000
                    )
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun Preview() {
    AppTheme {
        IntakesBlock(
            intakes = listOf()
        )
    }
}