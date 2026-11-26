
package com.example.studyquest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.studyquest.data.repository.DictionaryRepository
import com.example.studyquest.data.repository.WordInsight
import java.io.IOException
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface DictionaryUiState {
    data object Idle : DictionaryUiState
    data object Loading : DictionaryUiState

    data class Success(
        val insight: WordInsight
    ) : DictionaryUiState

    data class Error(
        val message: String
    ) : DictionaryUiState
}

class DictionaryViewModel(
    private val repository: DictionaryRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<DictionaryUiState>(DictionaryUiState.Idle)

    val uiState: StateFlow<DictionaryUiState> =
        _uiState.asStateFlow()

    private var lookupJob: Job? = null

    fun lookupWord(word: String) {
        lookupJob?.cancel()

        _uiState.value = DictionaryUiState.Loading

        lookupJob = viewModelScope.launch {
            val result = repository.getWordInsight(word)

            // Do not display results from a cancelled request.
            currentCoroutineContext().ensureActive()

            _uiState.value = result.fold(
                onSuccess = { insight ->
                    DictionaryUiState.Success(insight)
                },
                onFailure = { exception ->
                    val message = when (exception) {
                        is IOException ->
                            "Network error. Check your connection and try again."

                        is HttpException ->
                            if (exception.code() == 404) {
                                "No dictionary entry was found for this word."
                            } else {
                                "Dictionary service is unavailable. Please try again."
                            }

                        else ->
                            "Unable to load the dictionary definition."
                    }

                    DictionaryUiState.Error(message)
                }
            )
        }
    }

    fun clearLookup() {
        lookupJob?.cancel()
        lookupJob = null
        _uiState.value = DictionaryUiState.Idle
    }

    class Factory(
        private val repository: DictionaryRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {
            if (modelClass.isAssignableFrom(
                    DictionaryViewModel::class.java
                )
            ) {
                return DictionaryViewModel(repository) as T
            }

            throw IllegalArgumentException(
                "Unknown ViewModel class."
            )
        }
    }
}
