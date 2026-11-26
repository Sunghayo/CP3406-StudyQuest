
package com.example.studyquest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyquest.ui.home.LandingScreen
import com.example.studyquest.ui.quiz.QuizScreen

private object Routes {
    const val HOME = "home"
    const val QUIZ = "quiz"
}

@Composable
fun StudyQuestNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

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
                }
            )
        }

        composable(Routes.QUIZ) {
            QuizScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
