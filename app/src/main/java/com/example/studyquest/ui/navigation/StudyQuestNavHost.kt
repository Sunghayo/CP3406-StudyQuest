
package com.example.studyquest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyquest.ui.home.LandingScreen
import com.example.studyquest.ui.quiz.QuizScreen
import com.example.studyquest.ui.settings.SettingsScreen
import com.example.studyquest.ui.statistics.StatisticsScreen
import com.example.studyquest.ui.viewmodel.QuizHistoryViewModel

private object Routes {
    const val HOME = "home"
    const val QUIZ = "quiz"
    const val SETTINGS = "settings"
    const val STATISTICS = "statistics"
}

@Composable
fun StudyQuestNavHost(
    historyViewModel: QuizHistoryViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    var difficulty by rememberSaveable {
        mutableStateOf("Intermediate")
    }

    var largeTextEnabled by rememberSaveable {
        mutableStateOf(false)
    }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            LandingScreen(
                onStartLearning = {
                    navController.navigate(Routes.QUIZ) {
                        launchSingleTop = true
                    }
                },
                onOpenSettings = {
                    navController.navigate(Routes.SETTINGS) {
                        launchSingleTop = true
                    }
                },
                onOpenStatistics = {
                    navController.navigate(Routes.STATISTICS) {
                        launchSingleTop = true
                    }
                },
                largeTextEnabled = largeTextEnabled
            )
        }

        composable(Routes.QUIZ) {
            QuizScreen(
                difficulty = difficulty,
                largeTextEnabled = largeTextEnabled,
                saveStatus = historyViewModel.saveStatus,
                onStartQuiz = {
                    historyViewModel.resetSaveStatus()
                },
                onQuizCompleted = { level, correct, total ->
                    historyViewModel.saveQuizResult(
                        difficulty = level,
                        correctAnswers = correct,
                        totalQuestions = total
                    )
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                difficulty = difficulty,
                largeTextEnabled = largeTextEnabled,
                onDifficultyChange = { difficulty = it },
                onLargeTextChange = {
                    largeTextEnabled = it
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.STATISTICS) {
            val sessions by historyViewModel.sessions
                .collectAsState()

            StatisticsScreen(
                sessions = sessions,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
