package com.mpauli.base.datetime

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class DateHelperTest {

    @Test
    fun `should map local date to string with formatter`() {
        // Given
        val localDate = LocalDate.parse("2025-09-01")

        // When
        val result = localDate.toStringWithFormat()

        // Then
        result shouldBeEqualTo "01.09.2025"
    }
}