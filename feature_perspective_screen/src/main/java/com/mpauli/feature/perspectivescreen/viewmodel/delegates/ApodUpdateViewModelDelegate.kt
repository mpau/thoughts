package com.mpauli.feature.perspectivescreen.viewmodel.delegates

import androidx.annotation.VisibleForTesting
import com.mpauli.base.res.ResourceProvider
import com.mpauli.core.app.apod.domain.usecase.GetApodUseCase
import com.mpauli.feature.base.mvi.MviViewModelDelegate
import com.mpauli.feature.base.mvi.MviViewModelEventManager
import com.mpauli.feature.base.ui.domain.model.FeedbackData
import com.mpauli.feature.perspectivescreen.R
import com.mpauli.feature.perspectivescreen.domain.model.toItemState
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewAction
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewEffect
import com.mpauli.feature.perspectivescreen.viewmodel.model.ViewEvent
import kotlinx.coroutines.launch
import timber.log.Timber

internal class ApodUpdateViewModelDelegate(
    private val getApodUseCase: GetApodUseCase,
    private val resourceProvider: ResourceProvider
) : MviViewModelDelegate<ViewEvent, ViewAction, ViewEffect>() {

    override fun onRegister(viewModelEventManager: MviViewModelEventManager) {
        viewModelEventManager.addHandler(::onApodUpdateTriggered)
    }

    @VisibleForTesting
    fun onApodUpdateTriggered(
        @Suppress("UNUSED_PARAMETER")
        event: ViewEvent.ApodUpdateTriggered
    ) {
        lifecycleScope.launch {
            Timber.d("Get apod of the day")

            getApodUseCase.run().fold(
                onSuccess = { apod ->
                    Timber.d("Update the apod")

                    process(ViewAction.UpdateApod(apod.toItemState()))
                },
                onFailure = {
                    Timber.d("No apod available")

                    sendEffect(
                        ViewEffect.ShowSnackBar(
                            FeedbackData(
                                message = resourceProvider.getString(R.string.snackbar_no_apod),
                                actionLabel = null
                            )
                        )
                    )
                }
            )
        }
    }
}
