package com.mpauli.core.apodapi.data.model

import com.mpauli.core.models.Apod
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ApodDtoTest {

    @Test
    fun `should map ApodDto to Apod`() {
        // Given
        val copyright = "Panther Observatory"
        val date = LocalDate.now()
        val explanation = "In this stunning cosmic vista, galaxy M81 is..."
        val hdUrl = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg"
        val mediaType = "image"
        val serviceVersion = "v1"
        val title = "Galaxy Wars: M81 versus M82"
        val url = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c25.jpg"

        val apodDto = ApodDto(
            copyright = copyright,
            date = date.toString(),
            explanation = explanation,
            hdUrl = hdUrl,
            mediaType = mediaType,
            serviceVersion = serviceVersion,
            title = title,
            url = url
        )

        // When
        val result = apodDto.toDomain()

        // Then
        val expected = Apod(
            copyright = copyright,
            date = date,
            explanation = explanation,
            hdUrl = hdUrl,
            mediaType = mediaType,
            serviceVersion = serviceVersion,
            title = title,
            url = url
        )
        result shouldBeEqualTo expected
    }
}
