package com.example.githubviewer.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyValueStorage @Inject constructor() {
    var authToken: String? = null
}