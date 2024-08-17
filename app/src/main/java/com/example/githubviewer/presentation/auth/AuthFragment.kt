package com.example.githubviewer.presentation.auth

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.githubviewer.R
import com.example.githubviewer.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private var binding : FragmentAuthBinding? = null
    private val viewModel : AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAuthBinding.bind(view)

        viewModel.checkSavedToken()


        binding?.apply {
            signBtn.setOnClickListener {
                viewModel.onSignButtonPressed(tokenInput.editText?.text.toString())
            }

            viewModel.state.observe(viewLifecycleOwner) { state ->
                if (state is AuthViewModel.State.InvalidInput) {
                    tokenInput.isErrorEnabled = true
                    tokenInput.error = state.reason
                } else {
                    tokenInput.isErrorEnabled = false
                }


                if(state is AuthViewModel.State.Loading){
                    signBtn.text = "WAIT..."
                    signBtn.isEnabled = false
                } else {
                    signBtn.text = "SIGN IN"
                    signBtn.isEnabled = true
                }
            }

            lifecycleScope.launch {
                viewModel.actions.collect {action ->
                    when (action) {
                        is AuthViewModel.Action.ShowError -> showError(action.message)
                        is AuthViewModel.Action.RouteToMain -> navigateToMain()
                    }
                }
            }
        }
    }

    private fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("ERROR")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun navigateToMain() {
        findNavController().navigate(R.id.action_authFragment_to_repositoriesListFragment)
    }
}