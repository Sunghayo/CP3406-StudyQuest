
package com.example.studyquest.ui.statistics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studyquest.data.local.QuizSessionEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StatisticsScreen(
    sessions: List<QuizSessionEntity>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val quizzesCompleted = sessions.size

    val questionsAnswered = sessions.sumOf {
        it.totalQuestions
    }

    val correctAnswers = sessions.sumOf {
        it.correctAnswers
    }

    val accuracy = if (questionsAnswered > 0) {
        correctAnswers * 100.0 / questionsAnswered
    } else {
        0.0
    }

    val accuracyText = String.format(
        Locale.ENGLISH,
        "%.1f%%",
        accuracy
    )

    val dateFormatter = SimpleDateFormat(
        "dd MMM yyyy, HH:mm",
        Locale.ENGLISH
    )

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
            value = quizzesCompleted.toString()
        )

        StatisticCard(
            title = "Questions Answered",
            value = questionsAnswered.toString()
        )

        StatisticCard(
            title = "Overall Accuracy",
            value = accuracyText
        )

        HorizontalDivider()

        Text(
            text = "Recent Learning Activity",
            style = MaterialTheme.typography.titleLarge
        )

        if (sessions.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "No learning sessions yet. " +
                            "Complete a quiz to see your progress.",
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            sessions.take(5).forEach { session ->
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = session.difficulty,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "${session.correctAnswers} / " +
                                    "${session.totalQuestions} correct",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = dateFormatter.format(
                                Date(session.completedAt)
                            ),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
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
