package com.mpauli.feature.perspectivescreen.domain.model

import com.mpauli.core.models.Apod
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ApodItemStateTest {

    private val copyright = "Panther Observatory"
    private val date = LocalDate.now()
    private val explanation = "In this stunning cosmic vista, galaxy M81 is..."
    private val hdUrl = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg"
    private val title = "Galaxy Wars: M81 versus M82"
    private val url = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c25.jpg"


    @Test
    fun `should map Apod to ApodItemState`() {
        // Given
        val testApod = Apod(
            copyright = copyright,
            date = date,
            explanation = explanation,
            hdUrl = hdUrl,
            mediaType = "image",
            serviceVersion = "v1",
            title = title,
            url = url
        )
        val resultApodItemState = ApodItemState(
            copyright = copyright,
            date = date,
            explanation = explanation,
            hdUrl = hdUrl,
            title = title,
            url = url
        )

        // When
        val result = testApod.toItemState()

        // Then
        result shouldBeEqualTo resultApodItemState
    }
}
