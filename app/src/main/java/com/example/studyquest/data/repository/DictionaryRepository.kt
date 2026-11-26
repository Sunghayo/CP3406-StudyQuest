
package com.example.studyquest.data.repository

import com.example.studyquest.data.remote.DictionaryApi

data class WordInsight(
    val word: String,
    val partOfSpeech: String,
    val definition: String,
    val example: String?
)

class DictionaryRepository(
    private val api: DictionaryApi
) {

    suspend fun getWordInsight(
        word: String
    ): Result<WordInsight> {
        return runCatching {
            val entry = api.getWord(word).firstOrNull()
                ?: error("No dictionary entry found.")

            val meaning = entry.meanings.firstOrNull {
                it.definitions.any { definition ->
                    definition.definition.isNotBlank()
                }
            } ?: error("No word meaning found.")

            val definition = meaning.definitions.first {
                it.definition.isNotBlank()
            }

            WordInsight(
                word = entry.word,
                partOfSpeech = meaning.partOfSpeech,
                definition = definition.definition,
                example = definition.example
            )
        }
    }
}
