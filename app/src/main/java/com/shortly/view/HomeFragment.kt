package com.shortly.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.shortly.R
import com.shortly.databinding.FragmentHomeBinding
import com.shortly.model.common.MessageUtil
import com.shortly.model.datamodel.HistoryModel
import com.shortly.model.datamodel.state.HistoryPagingViewState
import com.shortly.model.datamodel.state.HistoryViewState
import com.shortly.model.datamodel.state.ShortenViewState
import com.shortly.model.usecase.CopyToClipboardUseCase
import com.shortly.view.adapter.HistoryAdapter
import com.shortly.view.adapter.HistoryPagingAdapter
import com.shortly.view.listener.HistoryDeleteListener
import com.shortly.viewmodel.HistoryViewModel
import com.shortly.viewmodel.ShortenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), HistoryDeleteListener {

    private val shortenViewModel: ShortenViewModel by viewModels()
    private val historyViewModel: HistoryViewModel by viewModels()

    @Inject
    lateinit var copyToClipboardUseCase: CopyToClipboardUseCase

    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: HistoryAdapter
    private lateinit var pagingAdapter: HistoryPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.buttonShorten.setOnClickListener {
            createShortenUrl(binding.urlEdittext.text.toString())
        }
        binding.urlEdittext.setOnClickListener {
            if(shortenViewModel.shortenViewStateFlow.value is ShortenViewState.Error) {
                shortenViewModel.resetState()
            }
        }
//        adapter = HistoryAdapter(
//            historyDeleteListener = this,
//            copyToClipboardUseCase = copyToClipboardUseCase
//        )
//        binding.recyclerView.adapter = adapter

        pagingAdapter = HistoryPagingAdapter(
            historyDeleteListener = this,
            copyToClipboardUseCase = copyToClipboardUseCase
        )
        binding.recyclerView.adapter = pagingAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            shortenViewModel.shortenViewStateFlow.collectLatest {
                processShortViewState(it)
            }
        }

        lifecycleScope.launch {
            historyViewModel.historyStateFlow.collectLatest {
//                processHistoryState(it)
            }
        }
        lifecycleScope.launch {
            historyViewModel.historyPagingStateFlow.collectLatest {
                when (it) {
                    is HistoryPagingViewState.NOTHING -> {
                    }
                    is HistoryPagingViewState.Success -> {
                        pagingAdapter.submitData(it.data)
                    }
                    else -> {
                        binding.infoContainer.visibility = View.VISIBLE
                        binding.historyContainer.visibility = View.GONE
                    }
                }
            }
        }
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest {
                Log.i("loadStateFlow", "$it -> ${it.refresh}")
                if(pagingAdapter.itemCount == 0) {
                    binding.infoContainer.visibility = View.VISIBLE
                    binding.historyContainer.visibility = View.GONE
                } else {
                    binding.infoContainer.visibility = View.GONE
                    binding.historyContainer.visibility = View.VISIBLE
                }
            }
        }
        loadHistory()
    }

    private fun createShortenUrl(url: String) {
        shortenViewModel.createShortUrl(url)
    }

    private fun processShortViewState(state: ShortenViewState) {
        binding.buttonShorten.isEnabled = true
        binding.progressBar.visibility = View.GONE
        binding.errorMessage.visibility = View.GONE
        binding.urlEdittext.setBackgroundResource(R.drawable.background_rect)

        when (state) {
            is ShortenViewState.DEFAULT -> {
                binding.urlEdittext.setText("")
            }
            is ShortenViewState.LOADING -> {
                binding.buttonShorten.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE
            }
            is ShortenViewState.SUCCESS -> {
                MessageUtil.showMessage(requireContext(), getString(R.string.message_url_generated_successfully))
                shortenViewModel.resetState()
            }
            is ShortenViewState.Error -> {
                binding.urlEdittext.setBackgroundResource(R.drawable.error_background_rect)
                binding.errorMessage.visibility = View.VISIBLE
                binding.errorMessage.text = state.exception.message
            }
        }
    }

    private fun processHistoryState(state: HistoryViewState) {
        when (state) {
            is HistoryViewState.NOTHING -> {
            }
            is HistoryViewState.Success -> {
                if (state.list.isEmpty()) {
                    binding.infoContainer.visibility = View.VISIBLE
                    binding.historyContainer.visibility = View.GONE
                } else {
                    binding.infoContainer.visibility = View.GONE
                    binding.historyContainer.visibility = View.VISIBLE
                }
                adapter.submitList(state.list)
            }
            else -> {
                binding.infoContainer.visibility = View.VISIBLE
                binding.historyContainer.visibility = View.GONE
            }
        }
    }

    private fun loadHistory() {
        historyViewModel.getPagingHistory()
    }

    override fun onDelete(historyModel: HistoryModel) {
        historyViewModel.deleteHistory(historyModel.id)
    }
}