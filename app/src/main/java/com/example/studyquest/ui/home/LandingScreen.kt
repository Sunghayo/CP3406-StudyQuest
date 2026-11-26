
package com.example.studyquest.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LandingScreen(
    onStartLearning: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenStatistics: () -> Unit,
    largeTextEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "STUDYQUEST",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Build Your Vocabulary",
            style = if (largeTextEnabled) {
                MaterialTheme.typography.headlineLarge
            } else {
                MaterialTheme.typography.headlineMedium
            },
            textAlign = TextAlign.Center
        )

        Text(
            text = "Learn academic English through interactive quizzes and track your progress.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Ready to Learn?",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Explore new academic words and improve your understanding.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Button(
                    onClick = onStartLearning,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start Learning")
                }
            }
        }

        OutlinedButton(
            onClick = onOpenStatistics,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("My Progress")
        }

        OutlinedButton(
            onClick = onOpenSettings,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Learning Settings")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Learn at your own pace. No account required.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}
