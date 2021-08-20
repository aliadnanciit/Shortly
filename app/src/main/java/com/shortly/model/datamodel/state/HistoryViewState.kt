package com.shortly.model.datamodel.state

import androidx.paging.PagingData
import com.shortly.model.datamodel.HistoryModel

sealed class HistoryViewState {

    object NOTHING : HistoryViewState()

    data class Success(val list: List<HistoryModel>): HistoryViewState()

    data class Error(val exception: Throwable): HistoryViewState()
}

sealed class HistoryPagingViewState {

    object NOTHING : HistoryPagingViewState()

    data class Success(val data: PagingData<HistoryModel>): HistoryPagingViewState()

    data class Error(val exception: Throwable): HistoryPagingViewState()
}