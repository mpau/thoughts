package com.mpauli.feature.thoughtviewer.viewmodel

import com.mpauli.feature.base.mvi.MviViewModel
import com.mpauli.feature.thoughtviewer.viewmodel.delegates.EditButtonClickedViewModelDelegate
import com.mpauli.feature.thoughtviewer.viewmodel.delegates.ThoughtDeletedViewModelDelegate
import com.mpauli.feature.thoughtviewer.viewmodel.delegates.ThoughtUpdateViewModelDelegate
import com.mpauli.feature.thoughtviewer.viewmodel.delegates.ThoughtUpsertedViewModelDelegate
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewAction
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEffect
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewEvent
import com.mpauli.feature.thoughtviewer.viewmodel.model.ViewState
import kotlinx.coroutines.CoroutineDispatcher

internal class ThoughtViewerViewModel(
    thoughtUpdateViewModelDelegate: ThoughtUpdateViewModelDelegate,
    thoughtUpsertedViewModelDelegate: ThoughtUpsertedViewModelDelegate,
    thoughtDeletedViewModelDelegate: ThoughtDeletedViewModelDelegate,
    editButtonClickedViewModelDelegate: EditButtonClickedViewModelDelegate,
    stateProcessor: ThoughtViewerStateProcessor,
    processorDispatcher: CoroutineDispatcher
) : MviViewModel<ViewState, ViewEvent, ViewAction, ViewEffect>(
    initialState = ViewState.DEFAULT,
    delegates = listOf(
        thoughtUpdateViewModelDelegate,
        thoughtUpsertedViewModelDelegate,
        thoughtDeletedViewModelDelegate,
        editButtonClickedViewModelDelegate
    ),
    stateProcessor = stateProcessor,
    processorDispatcher = processorDispatcher
)
