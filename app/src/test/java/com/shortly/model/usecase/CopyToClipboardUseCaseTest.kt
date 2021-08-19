package com.shortly.model.usecase

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import com.shortly.model.common.AppClipboardManager
import com.shortly.model.common.BuildSdkVersionChecker
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CopyToClipboardUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var clipboardManager: ClipboardManager

    @MockK
    private lateinit var buildSdkVersionChecker: BuildSdkVersionChecker
    @MockK
    private lateinit var appClipboardManager: AppClipboardManager

    private lateinit var copyToClipboardUseCase: CopyToClipboardUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        copyToClipboardUseCase = CopyToClipboardUseCase(buildSdkVersionChecker, appClipboardManager)
    }

    @Test
    fun `verify copy operation for OS versions after honeycomb`()  {
        val url = "http://abc.com"
        every { buildSdkVersionChecker.getSdkVersion() } returns Build.VERSION_CODES.JELLY_BEAN

        every { appClipboardManager.getSystemClipboardManager() } returns clipboardManager
        val clipData = mockk<ClipData>()
        every { appClipboardManager.getClipData(url) } returns clipData

        copyToClipboardUseCase.copy(url)

        verify { clipboardManager.setPrimaryClip(clipData) }
    }
}