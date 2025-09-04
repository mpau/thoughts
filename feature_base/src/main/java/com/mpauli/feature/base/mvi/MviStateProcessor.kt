package com.mpauli.feature.base.mvi

interface MviStateProcessor<S, A> {

    suspend fun process(action: A): S
}
