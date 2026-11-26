
package com.example.studyquest.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.studyquest.domain.quiz.QuizBank
import com.example.studyquest.domain.quiz.QuizLogic
import com.example.studyquest.ui.viewmodel.DictionaryUiState
import com.example.studyquest.ui.viewmodel.SaveStatus

@Composable
fun QuizScreen(
    difficulty: String,
    largeTextEnabled: Boolean,
    saveStatus: SaveStatus,
    dictionaryState: DictionaryUiState,
    onStartQuiz: () -> Unit,
    onQuizCompleted: (String, Int, Int) -> Unit,
    onLookupWord: (String) -> Unit,
    onClearLookup: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val questions = remember(difficulty) {
        QuizBank.questionsFor(difficulty)
    }

    var started by rememberSaveable(difficulty) {
        mutableStateOf(false)
    }

    var finished by rememberSaveable(difficulty) {
        mutableStateOf(false)
    }

    var questionIndex by rememberSaveable(difficulty) {
        mutableIntStateOf(0)
    }

    var selectedOption by rememberSaveable(difficulty) {
        mutableIntStateOf(-1)
    }

    var correctAnswers by rememberSaveable(difficulty) {
        mutableIntStateOf(0)
    }

    fun restartQuiz() {
        onStartQuiz()
        onClearLookup()

        questionIndex = 0
        selectedOption = -1
        correctAnswers = 0
        finished = false
        started = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "STUDYQUEST",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge
        )

        when {
            !started -> {
                Text(
                    text = "Vocabulary Quiz",
                    style = if (largeTextEnabled) {
                        MaterialTheme.typography.headlineLarge
                    } else {
                        MaterialTheme.typography.headlineMedium
                    },
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Difficulty: $difficulty",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Answer 10 vocabulary questions. " +
                            "Read each definition and select " +
                            "the matching English word.",
                    style = MaterialTheme.typography.bodyLarge
                )

                Button(
                    onClick = { restartQuiz() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start Quiz")
                }
            }

            finished -> {
                val accuracy = QuizLogic.accuracyPercent(
                    correctAnswers,
                    questions.size
                )

                Text(
                    text = "Quiz Completed!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Your Results",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = "$correctAnswers / ${questions.size} Correct",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = "Accuracy: $accuracy%",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                when (saveStatus) {
                    SaveStatus.IDLE -> {
                        Text(
                            text = "Your results have not been saved.",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Button(
                            onClick = {
                                onQuizCompleted(
                                    difficulty,
                                    correctAnswers,
                                    questions.size
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Save Results")
                        }
                    }

                    SaveStatus.SAVING -> {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = "Saving your results...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    SaveStatus.SAVED -> {
                        Text(
                            text = "Results saved successfully!",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    SaveStatus.ERROR -> {
                        Text(
                            text = "Could not save your results. " +
                                    "Please try again.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Button(
                            onClick = {
                                onQuizCompleted(
                                    difficulty,
                                    correctAnswers,
                                    questions.size
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Retry Saving")
                        }
                    }
                }

                Button(
                    onClick = { restartQuiz() },
                    enabled = saveStatus != SaveStatus.SAVING,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Try Again")
                }
            }

            else -> {
                val question = questions[questionIndex]

                val correctWord =
                    question.options[question.correctIndex]

                Text(
                    text = "Question ${questionIndex + 1} of ${questions.size}",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Difficulty: $difficulty",
                    style = MaterialTheme.typography.bodyMedium
                )

                LinearProgressIndicator(
                    progress = {
                        (questionIndex + 1).toFloat() / questions.size
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Which word matches this definition?",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = question.definition,
                            style = if (largeTextEnabled) {
                                MaterialTheme.typography.titleLarge
                            } else {
                                MaterialTheme.typography.bodyLarge
                            }
                        )
                    }
                }

                question.options.forEachIndexed { index, option ->
                    OutlinedButton(
                        onClick = {
                            if (selectedOption == -1) {
                                selectedOption = index

                                if (QuizLogic.isCorrect(question, index)) {
                                    correctAnswers++
                                }
                            }
                        },
                        enabled = selectedOption == -1,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${'A' + index}. $option",
                            modifier = Modifier.fillMaxWidth(),
                            style = if (largeTextEnabled) {
                                MaterialTheme.typography.titleMedium
                            } else {
                                MaterialTheme.typography.bodyLarge
                            }
                        )
                    }
                }

                if (selectedOption != -1) {
                    val correct = QuizLogic.isCorrect(
                        question,
                        selectedOption
                    )

                    Text(
                        text = if (correct) {
                            "Correct Answer!"
                        } else {
                            "Incorrect Answer"
                        },
                        style = MaterialTheme.typography.titleLarge,
                        color = if (correct) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )

                    Text(
                        text = "Correct word: $correctWord",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = question.explanation,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    HorizontalDivider()

                    Text(
                        text = "Dictionary Insights",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Explore an additional dictionary meaning online.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    when (val lookup = dictionaryState) {
                        DictionaryUiState.Idle -> {
                            OutlinedButton(
                                onClick = {
                                    onLookupWord(correctWord)
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("View Dictionary Definition")
                            }
                        }

                        DictionaryUiState.Loading -> {
                            Row(
                                horizontalArrangement =
                                    Arrangement.spacedBy(12.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp
                                )

                                Text(
                                    text = "Loading dictionary definition...",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        is DictionaryUiState.Success -> {
                            ElevatedCard(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement =
                                        Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = lookup.insight.word,
                                        style = MaterialTheme.typography.titleLarge
                                    )

                                    Text(
                                        text = lookup.insight.partOfSpeech,
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    Text(
                                        text = lookup.insight.definition,
                                        style = MaterialTheme.typography.bodyLarge
                                    )

                                    lookup.insight.example
                                        ?.takeIf { it.isNotBlank() }
                                        ?.let { example ->
                                            Text(
                                                text = "Example: $example",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }

                                    Text(
                                        text = "Source: Free Dictionary API",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }

                            Text(
                                text = "Dictionary meanings may differ from the quiz context.",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        is DictionaryUiState.Error -> {
                            Text(
                                text = lookup.message,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            OutlinedButton(
                                onClick = {
                                    onLookupWord(correctWord)
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Retry Dictionary Lookup")
                            }
                        }
                    }

                    Button(
                        onClick = {
                            onClearLookup()

                            if (questionIndex < questions.lastIndex) {
                                questionIndex++
                                selectedOption = -1
                            } else {
                                finished = true

                                onQuizCompleted(
                                    difficulty,
                                    correctAnswers,
                                    questions.size
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (questionIndex == questions.lastIndex) {
                                "View Results"
                            } else {
                                "Next Question"
                            }
                        )
                    }
                }
            }
        }

        HorizontalDivider()

        OutlinedButton(
            onClick = {
                onClearLookup()
                onBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}
