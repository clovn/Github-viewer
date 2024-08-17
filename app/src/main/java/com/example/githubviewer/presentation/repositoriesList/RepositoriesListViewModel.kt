package com.example.githubviewer.presentation.repositoriesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubviewer.data.AppRepository
import com.example.githubviewer.domain.LogOutUseCase
import com.example.githubviewer.models.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class RepositoriesListViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var appRepository : AppRepository
    @Inject lateinit var logOutUseCase : LogOutUseCase

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State> get() = _state

    fun loadView(){
        _state.value = State.Loading
        viewModelScope.launch {
            try {

                val list = appRepository.getRepositories()

                if(list.isEmpty()) {
                    _state.value = State.Error("connection error: HTTP 404")
                } else {
                    _state.value = State.Loaded(list)
                }

            } catch (e : Exception) {
                _state.value = State.Error("error: ${e.message}")
            }
        }
    }

    sealed interface State {
        data object Loading : State
        data class Loaded(val repos: List<Repo>) : State
        data class Error(val error: String) : State
        data object Empty : State
    }
}