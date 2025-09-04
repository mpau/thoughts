package com.mpauli.feature.thoughtviewer.viewmodel.delegates

import androidx.annotation.VisibleForTesting
import com.mpauli.core.app.thoughts.domain.usecase.DeleteThoughtUseCase
import com.mpauli.feature.base.mvi.MviViewModelDelegate
import com.mpauli.feature.base.mvi.MviViewModelEventManager
import com.mpauli.feature.thoughtviewer.domain.model.toDomain
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewAction
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEffect
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEvent
import kotlinx.coroutines.launch
import timber.log.Timber

internal class ThoughtDeletedViewModelDelegate(
    private val deleteThoughtUseCase: DeleteThoughtUseCase,
) : MviViewModelDelegate<ViewEvent, ViewAction, ViewEffect>() {

    override fun onRegister(viewModelEventManager: MviViewModelEventManager) {
        viewModelEventManager.addHandler(::onThoughtDeleted)
    }

    @VisibleForTesting
    fun onThoughtDeleted(event: ViewEvent.ThoughtDeleted) {
        lifecycleScope.launch {
            Timber.d("Delete the thought with id = ${event.thoughtItemState.id}")

            val thought = event.thoughtItemState.toDomain()
            deleteThoughtUseCase.run(thought)

            sendEffect(ViewEffect.Close)
        }
    }
}
