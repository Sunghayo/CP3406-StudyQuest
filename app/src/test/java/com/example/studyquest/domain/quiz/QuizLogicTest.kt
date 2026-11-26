
package com.example.studyquest.domain.quiz

import org.junit.Assert.*
import org.junit.Test

class QuizLogicTest {

    @Test
    fun correctAnswerReturnsTrue() {
        val question = QuizBank.questionsFor("Beginner").first()

        assertTrue(
            QuizLogic.isCorrect(question, question.correctIndex)
        )
    }

    @Test
    fun wrongAnswerReturnsFalse() {
        val question = QuizBank.questionsFor("Beginner").first()
        val wrongIndex = question.options.indices.first {
            it != question.correctIndex
        }

        assertFalse(
            QuizLogic.isCorrect(question, wrongIndex)
        )
    }

    @Test
    fun accuracyCalculationIsCorrect() {
        assertEquals(80, QuizLogic.accuracyPercent(8, 10))
        assertEquals(100, QuizLogic.accuracyPercent(10, 10))
        assertEquals(0, QuizLogic.accuracyPercent(0, 10))
    }

    @Test
    fun zeroQuestionsReturnsZeroAccuracy() {
        assertEquals(0, QuizLogic.accuracyPercent(0, 0))
    }

    @Test
    fun everyDifficultyHasTenValidQuestions() {
        listOf(
            "Beginner",
            "Intermediate",
            "Advanced"
        ).forEach { difficulty ->
            val questions = QuizBank.questionsFor(difficulty)

            assertEquals(10, questions.size)

            questions.forEach { question ->
                assertEquals(4, question.options.size)
                assertEquals(4, question.options.distinct().size)
                assertTrue(
                    question.correctIndex in question.options.indices
                )
                assertTrue(question.explanation.isNotBlank())
            }
        }
    }
}
