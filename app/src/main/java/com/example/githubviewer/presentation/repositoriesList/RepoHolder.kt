package com.example.githubviewer.presentation.repositoriesList

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.githubviewer.databinding.ItemRepositoryBinding
import com.example.githubviewer.models.Repo

class RepoHolder(
    private val binding: ItemRepositoryBinding,
    private val click : (Int) -> Unit
) : ViewHolder(binding.root) {

    fun onBind(repo : Repo) {
        binding.apply {
            repoName.text = repo.name
            repoDescription.text = repo.description
            repLanguage.text = repo.language

            root.setOnClickListener {
                click(repo.id)
                println(repo)
            }
        }
    }
}