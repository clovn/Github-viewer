package com.example.githubviewer.data

import com.example.githubviewer.models.ReadmeFile
import com.example.githubviewer.models.Repo
import com.example.githubviewer.models.RepoDetails
import com.example.githubviewer.models.UserInfo
import javax.inject.Inject


class AppRepository @Inject constructor(
    private val githubService: GithubService,
    private val keyValueStorage: KeyValueStorage
) {


    suspend fun getRepositories(): List<Repo> {
        return githubService.getRepos("Bearer ${keyValueStorage.authToken}")
    }

    suspend fun getRepository(repoId: Int): RepoDetails {
        return githubService.getRepoDetails(repoId, "Bearer ${keyValueStorage.authToken}")
    }

    suspend fun getRepositoryReadme(repoId: Int): ReadmeFile {
        return githubService.getReadmeFile(repoId, "Bearer ${keyValueStorage.authToken}")
    }

    suspend fun signIn(token: String): UserInfo {
        val data = githubService.signIn("Bearer $token")
        keyValueStorage.authToken = token
        return data
    }
}