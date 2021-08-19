package com.shortly.model.datamodel.state

sealed class ShortenViewState {

    object DEFAULT: ShortenViewState()

    object LOADING: ShortenViewState()

    object SUCCESS: ShortenViewState()

    data class Error(val exception: Throwable): ShortenViewState()
}