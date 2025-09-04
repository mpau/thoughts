package com.mpauli.base.data

import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <T> tryAsResult(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}
