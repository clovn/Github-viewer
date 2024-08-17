package com.example.githubviewer.presentation.detailInfo

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.githubviewer.R
import com.example.githubviewer.data.AppRepository
import com.example.githubviewer.databinding.FragmentDetailInfoBinding
import com.example.githubviewer.domain.LogOutUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailInfoFragment : Fragment(R.layout.fragment_detail_info) {

    @Inject lateinit var appRepository: AppRepository
    @Inject lateinit var logOutUseCase: LogOutUseCase
    private var binding : FragmentDetailInfoBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailInfoBinding.bind(view)

        binding

    }
}