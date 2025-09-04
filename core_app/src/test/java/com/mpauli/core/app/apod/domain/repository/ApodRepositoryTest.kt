package com.mpauli.core.app.apod.domain.repository

import com.mpauli.core.apodapi.data.ApodApi
import com.mpauli.core.models.Apod
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

internal class ApodRepositoryTest {

    private val apodApi: ApodApi = mock()
    private val apodRepository = ApodRepository(apodApi)

    private val date = LocalDate.now()

    @Test
    fun `should call ApodApi#getApod`() = runTest {
        // When
        apodRepository.get(date)

        // Then
        verify(apodApi).getApod(date)
    }

    @Test
    fun `should return success when api returns data`() = runTest {
        // Given
        val apod = Apod(
            copyright = "Panther Observatory",
            date = date,
            explanation = "In this stunning cosmic vista, galaxy M81 is...",
            hdUrl = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
            mediaType = "image",
            serviceVersion = "v1",
            title = "Galaxy Wars: M81 versus M82",
            url = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c25.jpg"
        )
        whenever(apodApi.getApod(date)) doReturn apod

        // When
        val result = apodRepository.get(date)

        // Then
        result.isSuccess shouldBeEqualTo true
    }

    @Test
    fun `should return failure when api throws exception`() = runTest {
        // Given
        whenever(apodApi.getApod(date)) doThrow IllegalStateException()

        // When
        val result = apodRepository.get(date)

        // Then
        result.isSuccess shouldBeEqualTo false
    }
}
