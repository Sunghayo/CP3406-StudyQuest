
package com.example.studyquest.domain.quiz

data class VocabularyQuestion(
    val definition: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

object QuizLogic {

    fun isCorrect(
        question: VocabularyQuestion,
        selectedIndex: Int
    ): Boolean {
        return selectedIndex == question.correctIndex &&
                selectedIndex in question.options.indices
    }

    fun accuracyPercent(
        correctAnswers: Int,
        totalQuestions: Int
    ): Int {
        if (totalQuestions <= 0) return 0

        val validCorrect = correctAnswers.coerceIn(
            0, totalQuestions
        )

        return validCorrect * 100 / totalQuestions
    }
}

object QuizBank {

    fun questionsFor(
        difficulty: String
    ): List<VocabularyQuestion> {
        return when (difficulty) {
            "Beginner" -> beginnerQuestions
            "Advanced" -> advancedQuestions
            else -> intermediateQuestions
        }
    }

    private val beginnerQuestions = listOf(
        VocabularyQuestion(
            "To successfully reach a goal.",
            listOf("Avoid", "Achieve", "Ignore", "Delay"),
            1,
            "Achieve means to accomplish a goal."
        ),
        VocabularyQuestion(
            "To make something smaller in amount.",
            listOf("Expand", "Observe", "Reduce", "Select"),
            2,
            "Reduce means to decrease an amount or size."
        ),
        VocabularyQuestion(
            "To find something for the first time.",
            listOf("Discover", "Maintain", "Avoid", "Improve"),
            0,
            "Discover means to find something previously unknown."
        ),
        VocabularyQuestion(
            "To stay away from something.",
            listOf("Assist", "Define", "Select", "Avoid"),
            3,
            "Avoid means to keep away from something."
        ),
        VocabularyQuestion(
            "To help or support someone.",
            listOf("Observe", "Assist", "Reduce", "Delay"),
            1,
            "Assist means to provide help or support."
        ),
        VocabularyQuestion(
            "To make something better.",
            listOf("Ignore", "Avoid", "Improve", "Define"),
            2,
            "Improve means to increase quality or performance."
        ),
        VocabularyQuestion(
            "To explain the meaning of a word.",
            listOf("Define", "Observe", "Reduce", "Select"),
            0,
            "Define means to state the meaning of something."
        ),
        VocabularyQuestion(
            "To watch something carefully.",
            listOf("Delay", "Assist", "Avoid", "Observe"),
            3,
            "Observe means to watch or notice carefully."
        ),
        VocabularyQuestion(
            "To choose from several possibilities.",
            listOf("Reduce", "Select", "Ignore", "Expand"),
            1,
            "Select means to choose an option."
        ),
        VocabularyQuestion(
            "To make something easier to understand.",
            listOf("Delay", "Avoid", "Clarify", "Reduce"),
            2,
            "Clarify means to make an idea clearer."
        )
    )

    private val intermediateQuestions = listOf(
        VocabularyQuestion(
            "To examine something carefully to understand it.",
            listOf("Maintain", "Analyse", "Assume", "Avoid"),
            1,
            "Analyse means to examine something in detail."
        ),
        VocabularyQuestion(
            "To judge the quality or importance of something.",
            listOf("Assume", "Indicate", "Evaluate", "Contrast"),
            2,
            "Evaluate means to assess quality or value."
        ),
        VocabularyQuestion(
            "To show or point out something.",
            listOf("Indicate", "Maintain", "Interpret", "Reduce"),
            0,
            "Indicate means to show or signal something."
        ),
        VocabularyQuestion(
            "To explain the meaning of information.",
            listOf("Establish", "Maintain", "Contrast", "Interpret"),
            3,
            "Interpret means to explain or understand meaning."
        ),
        VocabularyQuestion(
            "To create or set up something.",
            listOf("Indicate", "Establish", "Assume", "Contrast"),
            1,
            "Establish means to create or set up something."
        ),
        VocabularyQuestion(
            "To keep something at the same level or condition.",
            listOf("Analyse", "Reduce", "Maintain", "Indicate"),
            2,
            "Maintain means to keep something in a condition."
        ),
        VocabularyQuestion(
            "Important or large enough to be noticed.",
            listOf("Significant", "Relevant", "Temporary", "Minor"),
            0,
            "Significant means important or noticeable."
        ),
        VocabularyQuestion(
            "To identify differences between things.",
            listOf("Maintain", "Establish", "Indicate", "Contrast"),
            3,
            "Contrast means to compare differences."
        ),
        VocabularyQuestion(
            "A method of dealing with a problem.",
            listOf("Evidence", "Approach", "Result", "Theory"),
            1,
            "Approach means a method or way of doing something."
        ),
        VocabularyQuestion(
            "Closely connected to the subject being discussed.",
            listOf("Random", "General", "Relevant", "Optional"),
            2,
            "Relevant means directly connected to a subject."
        )
    )

    private val advancedQuestions = listOf(
        VocabularyQuestion(
            "Based on observation or experimental evidence.",
            listOf("Abstract", "Hypothetical", "Empirical", "Ambiguous"),
            2,
            "Empirical describes evidence based on observation."
        ),
        VocabularyQuestion(
            "To reach a conclusion using evidence.",
            listOf("Infer", "Mitigate", "Assume", "Contrast"),
            0,
            "Infer means to draw a conclusion from evidence."
        ),
        VocabularyQuestion(
            "A model or framework for understanding ideas.",
            listOf("Variable", "Hypothesis", "Exception", "Paradigm"),
            3,
            "Paradigm means a model or framework."
        ),
        VocabularyQuestion(
            "Having more than one possible interpretation.",
            listOf("Explicit", "Ambiguous", "Empirical", "Rigorous"),
            1,
            "Ambiguous means open to multiple interpretations."
        ),
        VocabularyQuestion(
            "To make a problem or harmful effect less severe.",
            listOf("Amplify", "Establish", "Mitigate", "Infer"),
            2,
            "Mitigate means to reduce a negative impact."
        ),
        VocabularyQuestion(
            "To combine information into a coherent whole.",
            listOf("Synthesize", "Extrapolate", "Corroborate", "Assume"),
            0,
            "Synthesize means to combine ideas or information."
        ),
        VocabularyQuestion(
            "A proposed explanation that can be tested.",
            listOf("Paradigm", "Variable", "Conclusion", "Hypothesis"),
            3,
            "A hypothesis is a testable proposed explanation."
        ),
        VocabularyQuestion(
            "To confirm a claim with additional evidence.",
            listOf("Extrapolate", "Corroborate", "Mitigate", "Infer"),
            1,
            "Corroborate means to support with further evidence."
        ),
        VocabularyQuestion(
            "To estimate beyond known data using existing trends.",
            listOf("Interpret", "Mitigate", "Extrapolate", "Clarify"),
            2,
            "Extrapolate means to estimate beyond known data."
        ),
        VocabularyQuestion(
            "Extremely careful, thorough, and precise.",
            listOf("Rigorous", "Ambiguous", "Optional", "Approximate"),
            0,
            "Rigorous describes careful and thorough work."
        )
    )
}
