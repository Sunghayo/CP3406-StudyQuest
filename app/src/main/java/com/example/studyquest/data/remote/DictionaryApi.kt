
package com.example.studyquest.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class DictionaryEntryDto(
    val word: String,
    val meanings: List<DictionaryMeaningDto>
)

data class DictionaryMeaningDto(
    val partOfSpeech: String,
    val definitions: List<DictionaryDefinitionDto>
)

data class DictionaryDefinitionDto(
    val definition: String,
    val example: String?
)

interface DictionaryApi {

    @GET("api/v2/entries/en/{word}")
    suspend fun getWord(
        @Path("word") word: String
    ): List<DictionaryEntryDto>
}

object DictionaryApiClient {

    private const val BASE_URL =
        "https://api.dictionaryapi.dev/"

    val service: DictionaryApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(DictionaryApi::class.java)
    }
}
