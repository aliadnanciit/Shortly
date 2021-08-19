package com.shortly.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shortly.model.datamodel.state.ShortenViewState
import com.shortly.model.usecase.CreateShortUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShortenViewModel @Inject constructor(
    private val createShortURLUseCase: CreateShortUrlUseCase
) : ViewModel() {

    private val _shortenViewStateFlow = MutableStateFlow<ShortenViewState>(ShortenViewState.DEFAULT)
    val shortenViewStateFlow: StateFlow<ShortenViewState> = _shortenViewStateFlow

    fun createShortUrl(url: String) {
        _shortenViewStateFlow.value = ShortenViewState.LOADING

        viewModelScope.launch {
            try {
                createShortURLUseCase.create(url)
                _shortenViewStateFlow.value = ShortenViewState.SUCCESS
            }
            catch (e: Exception) {
                _shortenViewStateFlow.value = ShortenViewState.Error(e)
            }
        }
    }

    fun resetState() {
        _shortenViewStateFlow.value = ShortenViewState.DEFAULT
    }
}