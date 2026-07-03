package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.PasswordEntry
import com.example.data.PasswordRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PasswordViewModel(private val repository: PasswordRepository) : ViewModel() {
    
    val passwords: StateFlow<List<PasswordEntry>> = repository.allPasswords
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addPassword(title: String, username: String, passwordHash: String, url: String) {
        viewModelScope.launch {
            repository.insert(
                PasswordEntry(
                    title = title,
                    username = username,
                    passwordHash = passwordHash,
                    url = url
                )
            )
        }
    }

    fun updatePassword(id: Int, title: String, username: String, passwordHash: String, url: String) {
        viewModelScope.launch {
            val existing = repository.getPasswordById(id)
            if (existing != null) {
                repository.update(
                    existing.copy(
                        title = title,
                        username = username,
                        passwordHash = passwordHash,
                        url = url
                    )
                )
            }
        }
    }

    fun deletePassword(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}

class PasswordViewModelFactory(private val repository: PasswordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PasswordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
