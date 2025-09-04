package com.mpauli.base.res

import android.content.Context
import android.content.res.Resources
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class AndroidResourceProviderTest {

    private val mockResources: Resources = mock()
    private val context: Context = mock {
        on { resources } doReturn mockResources
    }

    private val testDispatcher = StandardTestDispatcher()
    private val provider = AndroidResourceProvider(context)

    @Test
    fun `should get string from resources`() = runTest(testDispatcher) {
        // Given
        val id = 321312
        val expected = "test text"
        whenever(context.getString(id)) doReturn expected

        // When
        val result = provider.getString(id)

        // Then
        result shouldBeEqualTo expected
    }
}
