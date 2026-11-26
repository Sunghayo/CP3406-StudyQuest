
package com.example.studyquest.data.repository

import com.example.studyquest.data.local.QuizSessionDao
import com.example.studyquest.data.local.QuizSessionEntity
import kotlinx.coroutines.flow.Flow

class QuizSessionRepository(
    private val dao: QuizSessionDao
) {

    val sessions: Flow<List<QuizSessionEntity>> =
        dao.observeAllSessions()

    suspend fun saveQuizResult(
        difficulty: String,
        correctAnswers: Int,
        totalQuestions: Int
    ) {
        require(totalQuestions > 0) {
            "Total questions must be greater than zero."
        }

        require(correctAnswers in 0..totalQuestions) {
            "Correct answers must be within the valid range."
        }

        val session = QuizSessionEntity(
            difficulty = difficulty,
            correctAnswers = correctAnswers,
            totalQuestions = totalQuestions
        )

        dao.insertSession(session)
    }
}
