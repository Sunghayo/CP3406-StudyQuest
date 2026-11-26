
package com.example.studyquest.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    difficulty: String,
    largeTextEnabled: Boolean,
    onDifficultyChange: (String) -> Unit,
    onLargeTextChange: (Boolean) -> Unit,
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
            text = "Learning Settings",
            style = if (largeTextEnabled) {
                MaterialTheme.typography.headlineLarge
            } else {
                MaterialTheme.typography.headlineMedium
            }
        )

        Text(
            text = "Personalise your learning experience.",
            style = MaterialTheme.typography.bodyLarge
        )

        HorizontalDivider()

        Text(
            text = "Difficulty Level",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Choose the vocabulary level that suits you.",
            style = MaterialTheme.typography.bodyMedium
        )

        listOf("Beginner", "Intermediate", "Advanced").forEach { level ->
            FilterChip(
                selected = difficulty == level,
                onClick = { onDifficultyChange(level) },
                label = {
                    Text(level)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        HorizontalDivider()

        Text(
            text = "Accessibility",
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Larger Text",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Increase the size of selected interface text.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Switch(
                checked = largeTextEnabled,
                onCheckedChange = onLargeTextChange
            )
        }

        HorizontalDivider()

        Text(
            text = "Privacy",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "StudyQuest does not require an account or location access.",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "Permanent preference storage will be added in a later development stage.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}
