package com.pichurchyk.fitflow.ui.screen.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pichurchyk.fitflow.common.ext.toDateString
import com.pichurchyk.fitflow.ui.theme.AppTheme
import java.util.Date

@Composable
fun Date(
    modifier: Modifier = Modifier,
    date: Date,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(6.dp),
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = date.toDateString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        Date(date = Date())
    }
}