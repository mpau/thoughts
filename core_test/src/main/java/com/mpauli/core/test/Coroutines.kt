package com.mpauli.core.test

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

object TestScope : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = EmptyCoroutineContext + CoroutineName("junit") + Dispatchers.Unconfined + Job()
}
