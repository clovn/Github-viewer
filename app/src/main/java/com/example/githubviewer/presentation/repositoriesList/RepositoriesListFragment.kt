package com.example.githubviewer.presentation.repositoriesList

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubviewer.R
import com.example.githubviewer.data.AppRepository
import com.example.githubviewer.databinding.FragmentRepositoriesListBinding
import com.example.githubviewer.models.Repo
import com.example.githubviewer.presentation.repositoriesList.RepositoriesListViewModel.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RepositoriesListFragment : Fragment(R.layout.fragment_repositories_list) {

    private var binding : FragmentRepositoriesListBinding? = null
    private val viewModel : RepositoriesListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRepositoriesListBinding.bind(view)

        binding?.apply {
            retryBtn.setOnClickListener {
                viewModel.loadView()
            }

            topAppBar.setOnMenuItemClickListener {
                viewModel.logOutUseCase.execute(findNavController(), R.id.action_repositoriesListFragment_to_authFragment)
                false
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding?.apply {

                loadingIv.visibility = if(state is State.Loading) View.VISIBLE else View.GONE
                emptyIv.visibility = if(state is State.Empty) View.VISIBLE else View.INVISIBLE
                errIv.visibility = if(state is State.Error) View.VISIBLE else View.GONE

                when (state) {
                    is State.Empty -> {
                        err.text = "Empty"
                        errDesc.text = "No repositories at the moment"
                        retryBtn.text = "REFRESH"
                        retryBtn.visibility = View.VISIBLE
                        err.setTextColor(resources.getColor(R.color.secondary))
                    }

                    is State.Error -> {
                        err.text = "Something error"
                        errDesc.text = state.error
                        retryBtn.text = "RETRY"
                        retryBtn.visibility = View.VISIBLE
                        err.setTextColor(resources.getColor(R.color.error))
                    }

                    else -> {
                        err.text = ""
                        errDesc.text = ""
                        retryBtn.visibility = View.GONE
                    }
                }

                if(state is State.Loaded){
                    initAdapter(state.repos)
                    repositoriesListRv.visibility = View.VISIBLE
                }
            }
        }

        viewModel.loadView()
    }

    private fun initAdapter(list: List<Repo>){
        binding?.apply {

            repositoriesListRv.adapter = RepoAdapter(
                list = list,
                click = { id ->
                    val bundle = Bundle()
                    bundle.putInt("id", id)

                    findNavController().navigate(
                        R.id.action_repositoriesListFragment_to_detailInfoFragment,
                        args = bundle
                    )
                }
            )

            repositoriesListRv.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}