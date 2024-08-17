package com.example.githubviewer.domain

import androidx.navigation.NavController
import com.example.githubviewer.R
import com.example.githubviewer.data.SharedPrefDataSource
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private var sharedPrefDataSource: SharedPrefDataSource
) {

    fun execute(navController: NavController, action : Int){
        navController.navigate(
            action
        )
        sharedPrefDataSource.clear()
    }

}