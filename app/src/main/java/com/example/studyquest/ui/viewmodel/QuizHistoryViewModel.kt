
package com.example.studyquest.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.studyquest.data.repository.QuizSessionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class SaveStatus {
    IDLE,
    SAVING,
    SAVED,
    ERROR
}

class QuizHistoryViewModel(
    private val repository: QuizSessionRepository
) : ViewModel() {

    val sessions = repository.sessions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    var saveStatus by mutableStateOf(SaveStatus.IDLE)
        private set

    fun resetSaveStatus() {
        if (saveStatus != SaveStatus.SAVING) {
            saveStatus = SaveStatus.IDLE
        }
    }

    fun saveQuizResult(
        difficulty: String,
        correctAnswers: Int,
        totalQuestions: Int
    ) {
        if (
            saveStatus == SaveStatus.SAVING ||
            saveStatus == SaveStatus.SAVED
        ) {
            return
        }

        saveStatus = SaveStatus.SAVING

        viewModelScope.launch {
            try {
                repository.saveQuizResult(
                    difficulty = difficulty,
                    correctAnswers = correctAnswers,
                    totalQuestions = totalQuestions
                )

                saveStatus = SaveStatus.SAVED
            } catch (exception: Exception) {
                saveStatus = SaveStatus.ERROR
            }
        }
    }

    class Factory(
        private val repository: QuizSessionRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {
            if (modelClass.isAssignableFrom(
                    QuizHistoryViewModel::class.java
                )
            ) {
                return QuizHistoryViewModel(repository) as T
            }

            throw IllegalArgumentException(
                "Unknown ViewModel class."
            )
        }
    }
}
