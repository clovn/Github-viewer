package com.example.githubviewer.data

import com.example.githubviewer.models.ReadmeFile
import com.example.githubviewer.models.Repo
import com.example.githubviewer.models.RepoDetails
import com.example.githubviewer.models.UserInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path


interface GithubService {
    @Headers("Accept: application/json")

    @GET("user")
    suspend fun signIn(@Header("Authorization") token : String) : UserInfo

    @GET("user/repos")
    suspend fun getRepos(@Header("Authorization") token: String) : List<Repo>

    @GET("repositories/{repo-id}")
    suspend fun getRepoDetails(@Path("repo-id") id : Int, @Header("Authorization") token : String) : RepoDetails

    @GET("repositories/{repo-id}/readme")
    suspend fun getReadmeFile(@Path("repo-id") id : Int, @Header("Authorization") token : String) : ReadmeFile
}