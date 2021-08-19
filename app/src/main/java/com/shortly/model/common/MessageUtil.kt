package com.shortly.model.common

import android.content.Context
import android.widget.Toast

class MessageUtil {

    companion object {
        fun showMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}