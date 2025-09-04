package com.mpauli.feature.dayscreen

import com.mpauli.feature.dayscreen.viewmodel.DayScreenStateProcessor
import com.mpauli.feature.dayscreen.viewmodel.DayScreenViewModel
import com.mpauli.feature.dayscreen.viewmodel.delegates.ThoughtsUpdateViewModelDelegate
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dayScreenModule = module {
    viewModel {
        DayScreenViewModel(
            thoughtsUpdateViewModelDelegate = get(),
            stateProcessor = get(),
            processorDispatcher = Dispatchers.Main
        )
    }

    factory { DayScreenStateProcessor() }
    factory { ThoughtsUpdateViewModelDelegate(get()) }
}
