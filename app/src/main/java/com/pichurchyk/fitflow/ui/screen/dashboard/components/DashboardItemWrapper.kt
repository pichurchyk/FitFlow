package com.pichurchyk.fitflow.ui.screen.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.ui.theme.AppTheme
import com.pichurchyk.fitflow.ui.theme.TextStyles

@Composable
fun DashboardItemWrapper(
    modifier: Modifier = Modifier,
    type: DashboardItemWrapperType = DashboardItemWrapperType.FULL,
    level: DashboardItemWrapperLevel = DashboardItemWrapperLevel.HIGH,
    title: String? = null,
    subtitle: String,
    mainText: String,
    additionalText: String? = null,
    content: @Composable() () -> Unit,
    needBottomRadius: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    val bottomRadius = if (needBottomRadius) 10.dp else 4.dp
    val topRadius = if (title != null) 10.dp else 4.dp

    val horizontalPaddings = when (level) {
        DashboardItemWrapperLevel.HIGH -> 0.dp
        DashboardItemWrapperLevel.MEDIUM -> 30.dp
        DashboardItemWrapperLevel.LOW -> 60.dp
    }

    Column(
        modifier = modifier
            .padding(start = horizontalPaddings)
    ) {
        title?.let {
            Text(
                text = title,
                style = TextStyles.titleLarge,
            )
        }

        Box(
            modifier = Modifier
                .padding(top = if (title == null) 4.dp else 10.dp)
                .fillMaxWidth()
                .background(
                    backgroundColor,
                    RoundedCornerShape(
                        topStart = topRadius,
                        topEnd = topRadius,
                        bottomStart = bottomRadius,
                        bottomEnd = bottomRadius
                    )
                )
                .padding(vertical = 10.dp, horizontal = 16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                when (type) {
                    DashboardItemWrapperType.FULL -> {
                        TextColumn(
                            subtitle = subtitle,
                            mainText = mainText,
                            additionalText = additionalText
                        )
                    }

                    DashboardItemWrapperType.SMALL -> {
                        TextRow(
                            subtitle = subtitle,
                            mainText = mainText,
                            additionalText = additionalText
                        )
                    }
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun TextColumn(
    subtitle: String,
    mainText: String,
    additionalText: String?
) {
    Column {
        Text(
            text = subtitle,
            style = TextStyles.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = mainText,
            style = TextStyles.bodyLarge,
        )

        additionalText?.let {
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = it,
                style = TextStyles.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun TextRow(
    subtitle: String,
    mainText: String,
    additionalText: String?
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                modifier = Modifier,
                text = mainText,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        additionalText?.let {
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

enum class DashboardItemWrapperType {
    FULL,
    SMALL
}

enum class DashboardItemWrapperLevel {
    HIGH,
    MEDIUM,
    LOW
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun Preview() {
    AppTheme {
        DashboardItemWrapper(
            title = "Intakes",
            subtitle = "Total",
            mainText = "Main text",
            additionalText = "Additional text",
            type = DashboardItemWrapperType.FULL,
            level = DashboardItemWrapperLevel.HIGH,
            content = {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.error, RoundedCornerShape(100.dp))
                )
            }
        )
    }
}