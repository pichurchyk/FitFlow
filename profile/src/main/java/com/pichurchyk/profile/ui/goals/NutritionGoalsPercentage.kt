package com.pichurchyk.profile.ui.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.common.ui.theme.AppTheme
import com.pichurchyk.fitflow.common.ui.theme.TextStyles
import com.pichurchyk.fitflow.common.ui.theme.color_calories
import com.pichurchyk.nutrition.model.goals.NutritionGoal
import com.pichurchyk.nutrition.ui.ext.getColor
import kotlin.math.roundToInt

@Composable
fun NutritionGoalsPercentage(
    modifier: Modifier,
    intakes: List<NutritionGoal>,
) {
    val totalCalories = intakes.sumOf { it.goalCalories }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .border(1.dp, color_calories, RoundedCornerShape(6.dp))
                .clip(RoundedCornerShape(8.dp))
        ) {
            Row {
                intakes.forEachIndexed { index, intake ->
                    val weight =
                        if (totalCalories > 0) (intake.goalCalories / totalCalories).toFloat() else 0f

                    val leftCorners = if (index == 0) 8.dp else 0.dp
                    val rightCorners = if (index == intakes.size) 8.dp else 0.dp

                    if (weight > 0) {
                        Box(
                            modifier = Modifier
                                .height(20.dp)
                                .weight(weight)
                                .background(
                                    intake.intakeType
                                        .getColor()
                                        .copy(alpha = 0.7f),
                                    RoundedCornerShape(
                                        topStart = leftCorners,
                                        bottomStart = leftCorners,
                                        topEnd = rightCorners,
                                        bottomEnd = rightCorners
                                    )
                                )
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 4.dp, start = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            intakes.forEach { intake ->
                val weight =
                    if (totalCalories > 0) (intake.goalCalories / totalCalories).toFloat() else 0f

                Text(
                    modifier = Modifier.weight(1f),
                    text = "${(weight * 100).roundToInt()}%",
                    style = TextStyles.labelLarge,
                    color = intake.intakeType.getColor(),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        NutritionGoalsItem(
            modifier = Modifier,
            title = "Title",
            value = "Value",
            subtitle = "Subtitle",
            bgColor = MaterialTheme.colorScheme.primary,
            onValueChanged = {}
        )
    }
}