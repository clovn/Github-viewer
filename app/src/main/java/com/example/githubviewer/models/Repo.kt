package com.example.githubviewer.models

import kotlinx.serialization.Serializable


@Serializable
data class Repo(
    val id: Int,
    val name: String,
    val language: String?,
    val description: String?
)

