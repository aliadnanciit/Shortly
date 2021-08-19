package com.shortly.model.repository

import com.shortly.model.common.ErrorHandler
import com.shortly.model.common.safeApiCall
import com.shortly.model.database.HistoryDao
import com.shortly.model.database.HistoryEntity
import com.shortly.model.exception.InvalidURLException
import com.shortly.model.datamodel.ShortURL
import com.shortly.model.service.ShortlyApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class ShortenRepositoryImpl @Inject constructor(
    private val shortlyApi: ShortlyApi,
    private val historyDao: HistoryDao,
    private val errorHandler: ErrorHandler,
    @Named("IO_DISPATCHER") private val ioDispatcher: CoroutineDispatcher
) : ShortenRepository {

    override suspend fun createShortenUrl(url: String): ShortURL {
        return withContext(ioDispatcher) {
            val record = historyDao.findByUrl(url)
            if(record != null) {
                throw InvalidURLException("url $url already exits.")
            }
            val response = safeApiCall {
                shortlyApi.createShortenUrl(url)
            }
            if (response.isSuccessful) {
                val shortURLResponse = response.body()!!
                saveLink(shortURLResponse.shortURL)
                shortURLResponse.shortURL
            } else {
                val error = errorHandler.parseError(response.errorBody())
                throw InvalidURLException(error.message)
            }
        }
    }

    private suspend fun saveLink(shortURL: ShortURL) {
        val historyEntity = shortURL.let {
            HistoryEntity(
                code = it.code,
                shortLink = it.shortLink,
                shortLink2 = it.shortLink2,
                fullShortLink = it.fullShortLink,
                fullShortLink2 = it.fullShortLink2,
                shareLink = it.shareLink,
                fullShareLink = it.fullShortLink,
                originalLink = it.originalLink
            )
        }
        historyDao.insert(historyEntity)
    }
}