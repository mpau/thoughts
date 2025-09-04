package com.mpauli.feature.overviewscreen

import com.mpauli.feature.overviewscreen.viewmodel.OverviewScreenStateProcessor
import com.mpauli.feature.overviewscreen.viewmodel.OverviewScreenViewModel
import com.mpauli.feature.overviewscreen.viewmodel.delegates.ThoughtAddedViewModelDelegate
import com.mpauli.feature.overviewscreen.viewmodel.delegates.ThoughtsUpdateViewModelDelegate
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val overviewScreenModule = module {
    viewModel {
        OverviewScreenViewModel(
            thoughtsUpdateViewModelDelegate = get(),
            thoughtAddedViewModelDelegate = get(),
            stateProcessor = get(),
            processorDispatcher = Dispatchers.Main
        )
    }

    factory { OverviewScreenStateProcessor() }
    factory { ThoughtsUpdateViewModelDelegate(get()) }
    factory {
        ThoughtAddedViewModelDelegate(
            upsertThoughtUseCase = get(),
            resourceProvider = get()
        )
    }
}
