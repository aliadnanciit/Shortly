package com.shortly.model.usecase

import android.os.Build
import com.shortly.model.common.AppClipboardManager
import com.shortly.model.common.BuildSdkVersionChecker
import javax.inject.Inject

class CopyToClipboardUseCase @Inject constructor(
    private val buildSdkVersionChecker: BuildSdkVersionChecker,
    private val appClipboardManager: AppClipboardManager
) {
    fun copy(url: String): Boolean {
        return try {
            val sdk = buildSdkVersionChecker.getSdkVersion()
            val clipboard = appClipboardManager.getSystemClipboardManager()
            if (sdk < Build.VERSION_CODES.HONEYCOMB) {
                clipboard.text = url
            } else {
                val clip = appClipboardManager.getClipData(url)
                clipboard.setPrimaryClip(clip)
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}