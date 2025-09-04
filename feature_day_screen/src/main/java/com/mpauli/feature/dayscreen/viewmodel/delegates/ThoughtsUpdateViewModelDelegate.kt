package com.mpauli.feature.dayscreen.viewmodel.delegates

import androidx.annotation.VisibleForTesting
import com.mpauli.core.app.thoughts.domain.usecase.GetThoughtsOfDayUseCase
import com.mpauli.feature.base.mvi.MviViewModelDelegate
import com.mpauli.feature.base.mvi.MviViewModelEventManager
import com.mpauli.feature.dayscreen.domain.model.toItemState
import com.mpauli.feature.dayscreen.viewmodel.model.ViewAction
import com.mpauli.feature.dayscreen.viewmodel.model.ViewEffect
import com.mpauli.feature.dayscreen.viewmodel.model.ViewEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

internal class ThoughtsUpdateViewModelDelegate(
    private val getThoughtsOfDayUseCase: GetThoughtsOfDayUseCase
) : MviViewModelDelegate<ViewEvent, ViewAction, ViewEffect>() {

    private var updateJob: Job? = null

    override fun onRegister(viewModelEventManager: MviViewModelEventManager) {
        viewModelEventManager.addHandler(::onThoughtsUpdateTriggered)
    }

    @VisibleForTesting
    fun onThoughtsUpdateTriggered(event: ViewEvent.ThoughtsUpdateTriggered) {
        updateJob?.cancel()
        updateJob = lifecycleScope.launch {
            val date = event.date
            Timber.d("Get all thoughts of the day ($date) and update the list")

            try {
                getThoughtsOfDayUseCase.run(date)
                    .distinctUntilChanged()
                    .collectLatest { thoughts ->
                        Timber.d("Update the thoughts list of the day $date (count=${thoughts.size})")

                        val thoughtItemStateList = thoughts.map { it.toItemState() }
                        process(ViewAction.UpdateThoughts(thoughtItemStateList))
                    }
            } catch (ce: CancellationException) {
                throw ce
            } catch (e: Exception) {
                Timber.e(e, "Failed to fetch thoughts of the day")
            }
        }
    }
}
