package com.mpauli.feature.perspectivescreen

import com.mpauli.feature.perspectivescreen.viewmodel.PerspectiveScreenStateProcessor
import com.mpauli.feature.perspectivescreen.viewmodel.PerspectiveScreenViewModel
import com.mpauli.feature.perspectivescreen.viewmodel.delegates.ApodUpdateViewModelDelegate
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val perspectiveScreenModule = module {
    viewModel {
        PerspectiveScreenViewModel(
            apodUpdateViewModelDelegate = get(),
            stateProcessor = get(),
            processorDispatcher = Dispatchers.Main
        )
    }

    factory { PerspectiveScreenStateProcessor() }
    factory {
        ApodUpdateViewModelDelegate(
            getApodUseCase = get(),
            resourceProvider = get()
        )
    }
}
