package com.shortly.model.usecase

import com.shortly.model.common.rule.UrlValidationRule
import com.shortly.model.exception.InvalidURLException
import com.shortly.model.datamodel.ShortURL
import com.shortly.model.repository.ShortenRepository
import javax.inject.Inject

class CreateShortUrlUseCase @Inject constructor(
    private val urlValidationRule: UrlValidationRule,
    private val shortenRepository: ShortenRepository
) {
    suspend fun create(url: String) : ShortURL {
        if(url.isBlank()) {
            throw InvalidURLException("Empty url")
        }

        val updatedUrl = url.replace(" ", "%20")
        if(urlValidationRule.isValid(updatedUrl).not()) {
            throw InvalidURLException("Invalid url")
        }
        return shortenRepository.createShortenUrl(updatedUrl)
    }
}