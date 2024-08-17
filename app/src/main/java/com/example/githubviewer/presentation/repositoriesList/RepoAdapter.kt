package com.example.githubviewer.presentation.repositoriesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubviewer.databinding.ItemRepositoryBinding
import com.example.githubviewer.models.Repo

class RepoAdapter(
    private var list : List<Repo>,
    private val click : (Int) -> Unit
) : RecyclerView.Adapter<RepoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoHolder = RepoHolder(
        ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        click
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RepoHolder, position: Int) {
        holder.onBind(list[position])
    }

    fun update(list : List<Repo>){
        this.list = list
        notifyDataSetChanged()
    }
}