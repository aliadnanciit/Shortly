package com.shortly.view.listener

import com.shortly.model.datamodel.HistoryModel

interface HistoryDeleteListener {

    fun onDelete(historyModel: HistoryModel)
}