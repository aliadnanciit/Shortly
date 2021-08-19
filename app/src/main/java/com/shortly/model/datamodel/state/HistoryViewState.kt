package com.shortly.model.datamodel.state

import com.shortly.model.datamodel.HistoryModel

sealed class HistoryViewState {

    object NOTHING : HistoryViewState()

    data class Success(val list: List<HistoryModel>): HistoryViewState()

    data class Error(val exception: Throwable): HistoryViewState()
}