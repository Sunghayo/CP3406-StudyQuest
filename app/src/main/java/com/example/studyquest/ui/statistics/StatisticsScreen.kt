
package com.example.studyquest.ui.statistics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "My Learning Progress",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Track your vocabulary learning journey.",
            style = MaterialTheme.typography.bodyLarge
        )

        HorizontalDivider()

        StatisticCard(
            title = "Quizzes Completed",
            value = "0"
        )

        StatisticCard(
            title = "Questions Answered",
            value = "0"
        )

        StatisticCard(
            title = "Overall Accuracy",
            value = "0%"
        )

        HorizontalDivider()

        Text(
            text = "Recent Learning Activity",
            style = MaterialTheme.typography.titleLarge
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "No learning sessions yet. Your results will appear here after quiz tracking is implemented.",
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}

@Composable
private fun StatisticCard(
    title: String,
    value: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
