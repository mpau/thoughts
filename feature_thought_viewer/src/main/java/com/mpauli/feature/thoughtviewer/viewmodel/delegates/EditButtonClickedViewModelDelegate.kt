package com.mpauli.feature.thoughtviewer.viewmodel.delegates

import androidx.annotation.VisibleForTesting
import com.mpauli.feature.base.mvi.MviViewModelDelegate
import com.mpauli.feature.base.mvi.MviViewModelEventManager
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewAction
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEffect
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEvent
import kotlinx.coroutines.launch

internal class EditButtonClickedViewModelDelegate() :
    MviViewModelDelegate<ViewEvent, ViewAction, ViewEffect>() {

    override fun onRegister(viewModelEventManager: MviViewModelEventManager) {
        viewModelEventManager.addHandler(::onEditButtonClicked)
    }

    @VisibleForTesting
    fun onEditButtonClicked(event: ViewEvent.EditButtonClicked) {
        lifecycleScope.launch {
            process(ViewAction.UpdateEditState(event.editState))
        }
    }
}
