package com.mpauli.feature.overviewscreen.viewmodel.delegates

import androidx.annotation.VisibleForTesting
import com.mpauli.base.res.ResourceProvider
import com.mpauli.core.app.thoughts.domain.usecase.UpsertThoughtUseCase
import com.mpauli.core.models.Thought
import com.mpauli.feature.base.mvi.MviViewModelDelegate
import com.mpauli.feature.base.mvi.MviViewModelEventManager
import com.mpauli.feature.base.ui.domain.model.FeedbackData
import com.mpauli.feature.overviewscreen.R
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewAction
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewEffect
import com.mpauli.feature.overviewscreen.viewmodel.model.ViewEvent
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

internal class ThoughtAddedViewModelDelegate(
    private val upsertThoughtUseCase: UpsertThoughtUseCase,
    private val resourceProvider: ResourceProvider
) : MviViewModelDelegate<ViewEvent, ViewAction, ViewEffect>() {

    override fun onRegister(viewModelEventManager: MviViewModelEventManager) {
        viewModelEventManager.addHandler(::onThoughtAdded)
    }

    @VisibleForTesting
    fun onThoughtAdded(event: ViewEvent.ThoughtAdded) {
        lifecycleScope.launch {
            if (event.title.isNotBlank() && event.text.isNotBlank()) {
                Timber.d("Add a thought")

                upsertThoughtUseCase.run(
                    Thought(
                        id = 0L,
                        date = LocalDate.now(),
                        title = event.title,
                        text = event.text
                    )
                )
            } else {
                Timber.d("Title and/or text is not filled out")

                sendEffect(
                    ViewEffect.ShowSnackBar(
                        FeedbackData(
                            message = resourceProvider.getString(R.string.snackbar_thought_not_saved),
                            actionLabel = null
                        )
                    )
                )
            }
        }
    }
}
