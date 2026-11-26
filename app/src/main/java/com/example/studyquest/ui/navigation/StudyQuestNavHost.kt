
package com.example.studyquest.ui.navigation

import androidx.compose.runtime.Composable
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

private object Routes {
    const val HOME = "home"
    const val QUIZ = "quiz"
    const val SETTINGS = "settings"
    const val STATISTICS = "statistics"
}

@Composable
fun StudyQuestNavHost(
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
                    navController.navigate(Routes.QUIZ)
                },
                onOpenSettings = {
                    navController.navigate(Routes.SETTINGS)
                },
                onOpenStatistics = {
                    navController.navigate(Routes.STATISTICS)
                },
                largeTextEnabled = largeTextEnabled
            )
        }

        composable(Routes.QUIZ) {
            QuizScreen(
                difficulty = difficulty,
                largeTextEnabled = largeTextEnabled,
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
                onLargeTextChange = { largeTextEnabled = it },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.STATISTICS) {
            StatisticsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
