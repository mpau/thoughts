package com.mpauli.feature.thoughtviewer.viewmodel.delegates

import androidx.annotation.VisibleForTesting
import com.mpauli.core.app.thoughts.domain.usecase.GetThoughtUseCase
import com.mpauli.feature.base.mvi.MviViewModelDelegate
import com.mpauli.feature.base.mvi.MviViewModelEventManager
import com.mpauli.feature.thoughtviewer.domain.model.toItemState
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewAction
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEffect
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEvent
import kotlinx.coroutines.launch
import timber.log.Timber

internal class ThoughtUpdateViewModelDelegate(
    private val getThoughtUseCase: GetThoughtUseCase
) : MviViewModelDelegate<ViewEvent, ViewAction, ViewEffect>() {

    override fun onRegister(viewModelEventManager: MviViewModelEventManager) {
        viewModelEventManager.addHandler(::onThoughtUpdateTriggered)
    }

    @VisibleForTesting
    fun onThoughtUpdateTriggered(event: ViewEvent.ThoughtUpdateTriggered) {
        lifecycleScope.launch {
            val id = event.id

            Timber.d("Get thought for id: $id")

            getThoughtUseCase.run(id).collect { thought ->
                process(ViewAction.UpdateThought(thought.toItemState()))
            }
        }
    }
}
