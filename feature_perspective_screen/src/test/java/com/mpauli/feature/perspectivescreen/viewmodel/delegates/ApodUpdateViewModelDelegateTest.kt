package com.mpauli.feature.perspectivescreen.viewmodel.delegates

import android.accounts.NetworkErrorException
import com.mpauli.base.res.ResourceProvider
import com.mpauli.core.app.apod.domain.usecase.GetApodUseCase
import com.mpauli.core.models.Apod
import com.mpauli.core.test.TestScope
import com.mpauli.feature.base.mvi.MviActionProcessor
import com.mpauli.feature.base.mvi.MviPlugins
import com.mpauli.feature.base.ui.domain.model.FeedbackData
import com.mpauli.feature.perspectivescreen.R
import com.mpauli.feature.perspectivescreen.domain.model.toItemState
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewAction
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewEffect
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

internal class ApodUpdateViewModelDelegateTest {

    private val resourceProvider: ResourceProvider = mock()
    private val getApodUseCase: GetApodUseCase = mock()
    private val apodUpdateViewModelDelegate = ApodUpdateViewModelDelegate(
        getApodUseCase = getApodUseCase,
        resourceProvider = resourceProvider
    )

    private val actionProcessor: MviActionProcessor<ViewAction> = mock()
    private val effectProcessor: MutableSharedFlow<ViewEffect> = MutableSharedFlow(replay = 1)
    private val coroutineScope = TestScope

    private val captorEvent = argumentCaptor<ViewAction>()

    private val apod = Apod(
        copyright = "Panther Observatory",
        date = LocalDate.now(),
        explanation = "In this stunning cosmic vista, galaxy M81 is...",
        hdUrl = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
        mediaType = "image",
        serviceVersion = "v1",
        title = "Galaxy Wars: M81 versus M82",
        url = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c25.jpg"
    )

    @BeforeEach
    fun setUp() {
        MviPlugins.initViewModelDelegateForTests(
            viewModelDelegate = apodUpdateViewModelDelegate,
            ap = actionProcessor,
            eff = effectProcessor,
            scope = coroutineScope
        )
    }

    @Test
    fun `should process ViewAction#ShowLoading`() = runTest {
        // Given
        whenever(getApodUseCase.run()) doReturn Result.success(apod)

        // When
        apodUpdateViewModelDelegate.onApodUpdateTriggered(ViewEvent.ApodUpdateTriggered)

        // Then
        verify(actionProcessor, times(2)).process(captorEvent.capture())
        captorEvent.firstValue shouldBeEqualTo ViewAction.ShowLoading
    }

    @Test
    fun `should process ViewAction#ApodUpdateTriggered`() = runTest {
        // Given
        whenever(getApodUseCase.run()) doReturn Result.success(apod)

        // When
        apodUpdateViewModelDelegate.onApodUpdateTriggered(ViewEvent.ApodUpdateTriggered)

        // Then
        val apodItemState = apod.toItemState()
        verify(actionProcessor, times(2)).process(captorEvent.capture())
        captorEvent.secondValue shouldBeEqualTo ViewAction.UpdateApod(apodItemState)
    }

    @Test
    fun `should process ViewAction#ShowError when url string is blank`() = runTest {
        // Given
        whenever(getApodUseCase.run()) doReturn Result.success(apod.copy(url = " "))

        // When
        apodUpdateViewModelDelegate.onApodUpdateTriggered(ViewEvent.ApodUpdateTriggered)

        // Then
        verify(actionProcessor, times(2)).process(captorEvent.capture())
        captorEvent.secondValue shouldBeEqualTo ViewAction.ShowError
    }

    @Test
    fun `should process ViewAction#ShowError with network error`() = runTest {
        // Given
        val messageText = "No apod at all"
        whenever(resourceProvider.getString(R.string.snackbar_no_apod)) doReturn messageText
        whenever(getApodUseCase.run()) doReturn Result.failure(NetworkErrorException())

        // When
        apodUpdateViewModelDelegate.onApodUpdateTriggered(ViewEvent.ApodUpdateTriggered)

        // Then
        verify(actionProcessor, times(2)).process(captorEvent.capture())
        captorEvent.secondValue shouldBeEqualTo ViewAction.ShowError
    }

    @Test
    fun `should call getApodUseCase`() = runTest {
        // Given
        whenever(getApodUseCase.run()) doReturn Result.success(apod)

        // When
        apodUpdateViewModelDelegate.onApodUpdateTriggered(ViewEvent.ApodUpdateTriggered)

        // Then
        verify(getApodUseCase).run()
    }

    @Test
    fun `should show snackBar when no apod is available`() = runTest {
        // Given
        val messageText = "No apod at all"
        whenever(getApodUseCase.run()) doReturn Result.failure(IllegalArgumentException())
        whenever(resourceProvider.getString(R.string.snackbar_no_apod)) doReturn messageText

        // When
        apodUpdateViewModelDelegate.onApodUpdateTriggered(ViewEvent.ApodUpdateTriggered)

        // Then
        val effect = effectProcessor.first { it is ViewEffect.ShowSnackBar }

        (effect as ViewEffect.ShowSnackBar).snackBarVisuals shouldBeEqualTo FeedbackData(
            message = messageText,
            actionLabel = null
        )
    }
}
