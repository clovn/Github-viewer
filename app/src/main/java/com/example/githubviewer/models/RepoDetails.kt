package com.example.githubviewer.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDetails(
    val id: Int,
    val name: String,
    @SerialName(value = "stargazers_count")
    val stars: Int,
    @SerialName(value = "forks_count")
    val forks : Int,
    val watchers : Int,
    @SerialName(value = "html_url")
    val url : String,
    val license : String?
)
