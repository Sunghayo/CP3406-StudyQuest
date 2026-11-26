
package com.example.studyquest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizSessionDao {

    @Insert
    suspend fun insertSession(session: QuizSessionEntity)

    @Query(
        "SELECT * FROM quiz_sessions " +
                "ORDER BY completedAt DESC, id DESC"
    )
    fun observeAllSessions(): Flow<List<QuizSessionEntity>>

    @Query("SELECT COUNT(*) FROM quiz_sessions")
    suspend fun getSessionCount(): Int

    @Query("DELETE FROM quiz_sessions")
    suspend fun deleteAllSessions()
}
