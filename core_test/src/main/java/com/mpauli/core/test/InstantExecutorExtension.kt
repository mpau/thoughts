package com.mpauli.core.test

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

@SuppressLint("RestrictedApi")
class InstantExecutorExtension : BeforeEachCallback, AfterEachCallback, BeforeAllCallback, AfterAllCallback {

    override fun beforeEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance()
            .setDelegate(object : TaskExecutor() {

                override fun executeOnDiskIO(runnable: Runnable) = runnable.run()

                override fun postToMainThread(runnable: Runnable) = runnable.run()

                override fun isMainThread(): Boolean = true
            })
    }

    override fun afterEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun beforeAll(context: ExtensionContext?) {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun afterAll(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }
}
