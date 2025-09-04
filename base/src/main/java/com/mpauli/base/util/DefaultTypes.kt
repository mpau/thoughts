package com.mpauli.base.util

typealias Action = () -> Unit
typealias Procedure<T> = (T) -> Unit
typealias SuspendProcedure<T> = suspend (T) -> Unit

object Actions {

    val NoOp: Action = {}
}
