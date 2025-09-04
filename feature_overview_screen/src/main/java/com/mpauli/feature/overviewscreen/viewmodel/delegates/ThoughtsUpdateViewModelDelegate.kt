package com.mpauli.feature.overviewscreen.viewmodel.delegates

import androidx.annotation.VisibleForTesting
import com.mpauli.core.app.thoughts.domain.usecase.GetAllThoughtsUseCase
import com.mpauli.feature.base.mvi.MviViewModelDelegate
import com.mpauli.feature.base.mvi.MviViewModelEventManager
import com.mpauli.feature.overviewscreen.domain.model.toItemState
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewAction
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewEffect
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

internal class ThoughtsUpdateViewModelDelegate(
    private val getAllThoughtsUseCase: GetAllThoughtsUseCase,
) : MviViewModelDelegate<ViewEvent, ViewAction, ViewEffect>() {

    private var updateJob: Job? = null

    override fun onRegister(viewModelEventManager: MviViewModelEventManager) {
        viewModelEventManager.addHandler(::onThoughtsUpdateTriggered)
    }

    @VisibleForTesting
    fun onThoughtsUpdateTriggered(
        @Suppress("UNUSED_PARAMETER")
        event: ViewEvent.ThoughtsUpdateTriggered
    ) {
        updateJob?.cancel()
        updateJob = lifecycleScope.launch {
            Timber.d("Get all thoughts and update the list")

            try {
                getAllThoughtsUseCase.run()
                    .distinctUntilChanged()
                    .collectLatest { thoughts ->
                        Timber.d("Update the thoughts list (count=${thoughts.size})")

                        val thoughtItemStateList = thoughts.map { it.toItemState() }
                        process(ViewAction.UpdateThoughts(thoughtItemStateList))
                    }
            } catch (ce: CancellationException) {
                throw ce
            } catch (e: Exception) {
                Timber.e(e, "Failed to fetch thoughts")
            }
        }
    }
}
