package com.mpauli.core.app.apod.domain.usecase

import com.mpauli.core.app.apod.domain.repository.ApodRepository
import com.mpauli.core.models.Apod
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

internal class GetApodUseCaseTest {

    private val apodRepository: ApodRepository = mock()
    private val getApodUseCase = GetApodUseCase(apodRepository)

    private val date = LocalDate.now()

    @Test
    fun `should get Apod from repository`() = runTest {
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
        whenever(apodRepository.get(date)) doReturn Result.success(apod)

        // When
        val result = getApodUseCase.run(date)

        // Then
        result.isSuccess shouldBeEqualTo true
        result.getOrNull() shouldBeEqualTo apod
    }

    @Test
    fun `should access repository properly and call the correct use case`() = runTest {
        // Given
        whenever(apodRepository.get(date)) doReturn Result.success(mock())

        // When
        getApodUseCase.run(date)

        // Then
        verify(apodRepository).get(date)
    }
}
