package com.shortly.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shortly.model.datamodel.state.HistoryViewState
import com.shortly.model.usecase.DeleteHistoryUseCase
import com.shortly.model.usecase.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase
) : ViewModel() {

    private val _historyStateFlow = MutableStateFlow<HistoryViewState>(HistoryViewState.NOTHING)
    val historyStateFlow: StateFlow<HistoryViewState> = _historyStateFlow

    fun getHistory() {
        viewModelScope.launch {
            getHistoryUseCase.getHistory().collect {
                _historyStateFlow.value = HistoryViewState.Success(it)
            }
        }
    }

    fun deleteHistory(id: Int) {
        viewModelScope.launch {
            deleteHistoryUseCase.delete(id)
        }
    }
}