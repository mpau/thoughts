package com.mpauli.core.network

import com.mpauli.core.network.client.KtorClientBuilder
import org.koin.dsl.module

val networkModule = module {
    single { KtorClientBuilder() }
}
