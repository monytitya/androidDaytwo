package com.example.androiddaytwo.UiState

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        loadUserData()
    }

    fun loadUserData() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                delay(2000)
                // Change state to success
                _uiState.value = UserUiState.Success(name = "Sok San")
            } catch (e: Exception) {
                if (e !is kotlinx.coroutines.CancellationException) {
                    _uiState.value = UserUiState.Error(message = "បរាជ័យក្នុងការទាញយកទិន្នន័យ")
                }
            }
        }
    }
}
