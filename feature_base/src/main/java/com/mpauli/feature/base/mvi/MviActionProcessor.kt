package com.mpauli.feature.base.mvi

fun interface MviActionProcessor<A> {

    fun process(action: A)
}
