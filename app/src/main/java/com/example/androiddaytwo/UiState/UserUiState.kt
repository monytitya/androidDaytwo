package com.example.androiddaytwo.UiState

sealed interface UserUiState {
    data object Loading : UserUiState
    data class Success(val name: String) : UserUiState
    data class Error(val message: String) : UserUiState
}
