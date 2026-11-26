
package com.example.studyquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyquest.data.local.StudyQuestDatabase
import com.example.studyquest.data.remote.DictionaryApiClient
import com.example.studyquest.data.repository.DictionaryRepository
import com.example.studyquest.data.repository.QuizSessionRepository
import com.example.studyquest.ui.navigation.StudyQuestNavHost
import com.example.studyquest.ui.theme.StudyQuestTheme
import com.example.studyquest.ui.viewmodel.DictionaryViewModel
import com.example.studyquest.ui.viewmodel.QuizHistoryViewModel

class MainActivity : ComponentActivity() {

    private val quizRepository by lazy {
        QuizSessionRepository(
            StudyQuestDatabase
                .getDatabase(applicationContext)
                .quizSessionDao()
        )
    }

    private val dictionaryRepository by lazy {
        DictionaryRepository(
            DictionaryApiClient.service
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val historyViewModel: QuizHistoryViewModel = viewModel(
                factory = QuizHistoryViewModel.Factory(
                    quizRepository
                )
            )

            val dictionaryViewModel: DictionaryViewModel = viewModel(
                factory = DictionaryViewModel.Factory(
                    dictionaryRepository
                )
            )

            StudyQuestTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    StudyQuestNavHost(
                        historyViewModel = historyViewModel,
                        dictionaryViewModel = dictionaryViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
