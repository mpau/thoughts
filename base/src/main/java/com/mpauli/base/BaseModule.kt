package com.mpauli.base

import com.mpauli.base.res.AndroidResourceProvider
import com.mpauli.base.res.ResourceProvider
import org.koin.dsl.module

val baseModule = module {
    factory<ResourceProvider> { AndroidResourceProvider(get()) }
}
