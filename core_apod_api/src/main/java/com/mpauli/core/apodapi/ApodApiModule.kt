package com.mpauli.core.apodapi

import com.mpauli.core.apodapi.data.ApodApi
import org.koin.dsl.module

val apodApiModule = module {
    single { ApodApi(get()) }
}
