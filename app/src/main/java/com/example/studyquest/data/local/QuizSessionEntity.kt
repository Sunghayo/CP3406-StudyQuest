
package com.example.studyquest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_sessions")
data class QuizSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val difficulty: String,
    val correctAnswers: Int,
    val totalQuestions: Int,
    val completedAt: Long = System.currentTimeMillis()
)
