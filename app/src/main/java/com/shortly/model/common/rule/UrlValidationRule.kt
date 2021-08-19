package com.shortly.model.common.rule

import android.webkit.URLUtil
import javax.inject.Inject

class UrlValidationRule @Inject constructor() {

    fun isValid(url: String): Boolean {
        return URLUtil.isValidUrl(url)
    }

}