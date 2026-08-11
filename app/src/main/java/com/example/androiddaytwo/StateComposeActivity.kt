package com.example.androiddaytwo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androiddaytwo.UiState.UserUiState
import com.example.androiddaytwo.UiState.UserViewModel

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ComposableUserScreen(
    userViewModel: UserViewModel = viewModel()
) {
    // តាមដាន StateFlow នៅក្នុង Compose
    val uiState by userViewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is UserUiState.Loading -> {
                CircularProgressIndicator()
            }
            is UserUiState.Success -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "ឈ្មោះអ្នកប្រើប្រាស់៖ ${state.name}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { userViewModel.loadUserData() }) {
                        Text("ទាញយកទិន្នន័យឡើងវិញ")
                    }
                }
            }
            is UserUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}