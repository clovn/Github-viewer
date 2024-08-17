package com.example.githubviewer.presentation.auth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubviewer.data.AppRepository
import com.example.githubviewer.data.SharedPrefDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var appRepository : AppRepository
    @Inject lateinit var sharedPref: SharedPrefDataSource

    private val token: MutableLiveData<String> = MutableLiveData()
    private val _state: MutableLiveData<State> = MutableLiveData(State.Idle)
    val state: LiveData<State> get() = _state

    private val _actions = MutableSharedFlow<Action>()
    val actions: Flow<Action> get() = _actions

    fun checkSavedToken(){
        val token = sharedPref.getToken()
        if(token != null){
            this@AuthViewModel.token.value = token
            authenticate()
        }
    }

    fun onSignButtonPressed(inputToken : String) {
        if (inputToken.isBlank()) {
            _state.value = State.InvalidInput("token cannot be empty")
            return
        }

        token.value = inputToken

        _state.value = State.Loading

        authenticate()

        sharedPref.saveToken(token.value!!)

    }

    private fun authenticate(){
        viewModelScope.launch {
            try {

                token.value?.let {
                    appRepository.signIn(it)
                }

                _actions.emit(Action.RouteToMain)

                _state.value = State.Idle
            } catch (e: HttpException) {
                _state.value = State.InvalidInput("invalid token")
            } catch (e: Exception) {
                _state.value = State.Idle
                _actions.emit(Action.ShowError("Authentication failed: ${e.message}"))
            }
        }
    }

    sealed interface State {
        data object Idle : State
        data object Loading : State
        data class InvalidInput(val reason: String) : State
    }

    sealed interface Action {
        data class ShowError(val message: String) : Action
        data object RouteToMain : Action
    }
}
