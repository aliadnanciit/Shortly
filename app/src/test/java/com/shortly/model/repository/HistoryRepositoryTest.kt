package com.shortly.model.repository

import app.cash.turbine.test
import com.shortly.model.database.HistoryDao
import com.shortly.model.database.HistoryEntity
import com.shortly.model.datamodel.HistoryModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

class HistoryRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var historyDao: HistoryDao

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var historyRepository: HistoryRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        historyRepository = HistoryRepositoryImpl(historyDao, testCoroutineDispatcher)
    }

    @Test
    fun `delete history`() = runBlockingTest {
        historyRepository.delete(123)

        coVerify { historyDao.delete(123) }
    }

    @ExperimentalTime
    @Test
    fun `verify empty history`() = runBlockingTest {
        coEvery { historyDao.getHistory() } returns flowOf(emptyList())

        historyRepository.getHistory().test {
            Assert.assertEquals(emptyList<HistoryEntity>(), expectItem())
            expectComplete()
        }
    }

    @ExperimentalTime
    @Test
    fun `verify history if records exists`() = runBlockingTest {
        coEvery { historyDao.getHistory() } returns flowOf(listOf(getHistoryEntity()))

        historyRepository.getHistory().test {
            Assert.assertEquals(listOf(getHistoryModel(getHistoryEntity())), expectItem())
            expectComplete()
        }
    }

    private fun getHistoryEntity() : HistoryEntity {
        return HistoryEntity(
            id = 1,
            code = "code",
            shortLink = "sl",
            shortLink2 = "sl2",
            fullShortLink = "fsl",
            shareLink = "shrl",
            fullShareLink = "f_shr_l"
        )
    }

    private fun getHistoryModel(historyEntity: HistoryEntity): HistoryModel {
        return HistoryModel(
            id = historyEntity.id!!,
            code = historyEntity.code,
            shortLink = historyEntity.shortLink,
            fullShortLink = historyEntity.fullShortLink,
            shortLink2 = historyEntity.shortLink2,
            fullShortLink2 = historyEntity.fullShortLink2,
            shareLink = historyEntity.shareLink,
            fullShareLink = historyEntity.fullShareLink
        )
    }
}