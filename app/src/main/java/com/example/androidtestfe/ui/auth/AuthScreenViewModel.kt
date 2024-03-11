package com.example.androidtestfe.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtestfe.data.localdatabase.model.ListModel
import com.example.androidtestfe.usecases.AddUserUseCase
import com.example.androidtestfe.usecases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UserUiState>(UserUiState.None)
    val uiState get() = _uiState

    fun addUser(model: ListModel) {
        viewModelScope.launch(Dispatchers.IO) {
            addUserUseCase.addUser(model)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = getUserUseCase.login(email, password)
                if (user.id != null) {
                    withContext(Dispatchers.Main) {
                        _uiState.value = UserUiState.Success
                    }
                    return@launch
                }
            } catch (e: NullPointerException) {
                withContext(Dispatchers.Main) {
                    _uiState.value = UserUiState.Fail
                }
            }
        }
    }
}

sealed class UserUiState {
    object None : UserUiState()
    object Success : UserUiState()
    object Fail : UserUiState()
}
