package com.shortly.model.common

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val CLIP_BOARD_LABEL = "app_clip_board_label"

class AppClipboardManager @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
) {

    fun getSystemClipboardManager() : ClipboardManager {
        return applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    fun getClipData(text: String) : ClipData {
        return ClipData.newPlainText(CLIP_BOARD_LABEL, text)
    }
}