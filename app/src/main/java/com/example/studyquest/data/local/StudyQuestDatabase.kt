
package com.example.studyquest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [QuizSessionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StudyQuestDatabase : RoomDatabase() {

    abstract fun quizSessionDao(): QuizSessionDao

    companion object {

        @Volatile
        private var INSTANCE: StudyQuestDatabase? = null

        fun getDatabase(context: Context): StudyQuestDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StudyQuestDatabase::class.java,
                    "studyquest_database"
                ).build().also { database ->
                    INSTANCE = database
                }
            }
        }
    }
}
