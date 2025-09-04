package com.mpauli.feature.thoughtviewer

import com.mpauli.feature.thoughtviewer.viewmodel.ThoughtViewerStateProcessor
import com.mpauli.feature.thoughtviewer.viewmodel.ThoughtViewerViewModel
import com.mpauli.feature.thoughtviewer.viewmodel.delegates.EditButtonClickedViewModelDelegate
import com.mpauli.feature.thoughtviewer.viewmodel.delegates.ThoughtDeletedViewModelDelegate
import com.mpauli.feature.thoughtviewer.viewmodel.delegates.ThoughtUpdateViewModelDelegate
import com.mpauli.feature.thoughtviewer.viewmodel.delegates.ThoughtUpsertedViewModelDelegate
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val thoughtViewerModule = module {
    viewModel {
        ThoughtViewerViewModel(
            thoughtUpdateViewModelDelegate = get(),
            thoughtUpsertedViewModelDelegate = get(),
            thoughtDeletedViewModelDelegate = get(),
            editButtonClickedViewModelDelegate = get(),
            stateProcessor = get(),
            processorDispatcher = Dispatchers.Main
        )
    }

    factory { ThoughtViewerStateProcessor() }
    factory { ThoughtUpdateViewModelDelegate(get()) }
    factory {
        ThoughtUpsertedViewModelDelegate(
            upsertThoughtUseCase = get(),
            resourceProvider = get()
        )
    }
    factory { ThoughtDeletedViewModelDelegate(get()) }
    factory { EditButtonClickedViewModelDelegate() }
}
