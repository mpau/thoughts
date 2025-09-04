package com.mpauli.feature.overviewscreen.viewmodel

import com.mpauli.base.res.ResourceProvider
import com.mpauli.core.app.thoughts.domain.usecase.GetAllThoughtsUseCase
import com.mpauli.core.app.thoughts.domain.usecase.UpsertThoughtUseCase
import com.mpauli.core.test.InstantExecutorExtension
import com.mpauli.feature.overviewscreen.viewmodel.delegates.ThoughtAddedViewModelDelegate
import com.mpauli.feature.overviewscreen.viewmodel.delegates.ThoughtsUpdateViewModelDelegate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class)
internal class OverviewScreenViewModelTest {

    private val getAllThoughtsUseCase: GetAllThoughtsUseCase = mock()
    private val resourceProvider: ResourceProvider = mock()
    private val thoughtsUpdateViewModelDelegate = ThoughtsUpdateViewModelDelegate(getAllThoughtsUseCase)

    private val upsertThoughtUseCase: UpsertThoughtUseCase = mock()
    private val thoughtAddedViewModelDelegate = ThoughtAddedViewModelDelegate(
        upsertThoughtUseCase = upsertThoughtUseCase,
        resourceProvider = resourceProvider
    )

    private val overviewScreenStateProcessor: OverviewScreenStateProcessor = mock()
    private val dispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        whenever(getAllThoughtsUseCase.run()) doReturn emptyFlow()
    }

    @Test
    fun `should send ViewEvent#ThoughtsUpdateTriggered event`() = runTest {
        // Given
        OverviewScreenViewModel(
            thoughtsUpdateViewModelDelegate = thoughtsUpdateViewModelDelegate,
            thoughtAddedViewModelDelegate = thoughtAddedViewModelDelegate,
            stateProcessor = overviewScreenStateProcessor,
            processorDispatcher = dispatcher
        )

        // When
        advanceUntilIdle()

        // Then
        @Suppress("UnusedFlow")
        verify(getAllThoughtsUseCase).run()
    }
}
