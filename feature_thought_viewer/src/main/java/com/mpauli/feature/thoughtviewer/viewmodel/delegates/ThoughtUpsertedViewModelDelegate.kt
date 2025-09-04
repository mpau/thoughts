package com.mpauli.feature.thoughtviewer.viewmodel.delegates

import androidx.annotation.VisibleForTesting
import com.mpauli.base.res.ResourceProvider
import com.mpauli.core.app.thoughts.domain.usecase.UpsertThoughtUseCase
import com.mpauli.feature.base.mvi.MviViewModelDelegate
import com.mpauli.feature.base.mvi.MviViewModelEventManager
import com.mpauli.feature.base.ui.domain.model.FeedbackData
import com.mpauli.feature.thoughtviewer.R
import com.mpauli.feature.thoughtviewer.domain.model.toDomain
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewAction
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEffect
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEvent
import kotlinx.coroutines.launch
import timber.log.Timber

internal class ThoughtUpsertedViewModelDelegate(
    private val upsertThoughtUseCase: UpsertThoughtUseCase,
    private val resourceProvider: ResourceProvider
) : MviViewModelDelegate<ViewEvent, ViewAction, ViewEffect>() {

    override fun onRegister(viewModelEventManager: MviViewModelEventManager) {
        viewModelEventManager.addHandler(::onThoughtUpserted)
    }

    @VisibleForTesting
    fun onThoughtUpserted(event: ViewEvent.ThoughtUpserted) {
        lifecycleScope.launch {
            val thought = event.thoughtItemState.toDomain()

            if (thought.title.isNotBlank() && thought.text.isNotBlank()) {
                Timber.d("Upsert the thought with id = ${thought.id}")

                upsertThoughtUseCase.run(thought)

                publishInternally(ViewEvent.EditButtonClicked(false))
            } else {
                Timber.d("Title and/or text is not filled out")

                sendEffect(
                    ViewEffect.ShowSnackBar(
                        FeedbackData(
                            message = resourceProvider.getString(R.string.snackbar_thought_not_updated),
                            actionLabel = null
                        )
                    )
                )
            }
        }
    }
}
