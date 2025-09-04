package com.mpauli.feature.perspectivescreen.viewmodel

import com.mpauli.base.res.ResourceProvider
import com.mpauli.core.app.apod.domain.usecase.GetApodUseCase
import com.mpauli.core.models.Apod
import com.mpauli.core.test.InstantExecutorExtension
import com.mpauli.feature.perspectivescreen.viewmodel.delegates.ApodUpdateViewModelDelegate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class)
internal class PerspectiveScreenViewModelTest {

    private val apodAsResult = Result.success(
        Apod(
            copyright = "Panther Observatory",
            date = LocalDate.now(),
            explanation = "In this stunning cosmic vista, galaxy M81 is...",
            hdUrl = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
            mediaType = "image",
            serviceVersion = "v1",
            title = "Galaxy Wars: M81 versus M82",
            url = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c25.jpg"
        )
    )
    private val getApodUseCase: GetApodUseCase = mock()
    private val resourceProvider: ResourceProvider = mock()
    private val apodUpdateViewModelDelegate = ApodUpdateViewModelDelegate(
        getApodUseCase = getApodUseCase,
        resourceProvider = resourceProvider
    )

    private val perspectiveScreenStateProcessor: PerspectiveScreenStateProcessor = mock()

    @Test
    fun `should send ViewEvent#ApodUpdateTriggered event`() = runTest {
        // Given
        whenever(getApodUseCase.run(any())) doReturn apodAsResult
        val dispatcher = StandardTestDispatcher(testScheduler)
        PerspectiveScreenViewModel(
            apodUpdateViewModelDelegate = apodUpdateViewModelDelegate,
            stateProcessor = perspectiveScreenStateProcessor,
            processorDispatcher = dispatcher
        )

        // When
        advanceUntilIdle()

        // Then
        verify(getApodUseCase).run()
    }
}
