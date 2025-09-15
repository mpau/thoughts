package com.mpauli.core.apodapi.data.model

import com.mpauli.core.models.Apod
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ApodDtoTest {

    private val copyright = "Panther Observatory"
    private val date = LocalDate.now()
    private val explanation = "In this stunning cosmic vista, galaxy M81 is..."
    private val hdUrl = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg"
    private val mediaType = "image"
    private val serviceVersion = "v1"
    private val title = "Galaxy Wars: M81 versus M82"
    private val url = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c25.jpg"

    private val apodDto = ApodDto(
        copyright = copyright,
        date = date.toString(),
        explanation = explanation,
        hdUrl = hdUrl,
        mediaType = mediaType,
        serviceVersion = serviceVersion,
        title = title,
        url = url
    )

    @Test
    fun `should map ApodDto to Apod`() {
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

    @Test
    fun `should map ApodDto to Apod with url equals null`() {
        // When
        val result = apodDto.copy(url = null).toDomain()

        // Then
        val expected = Apod(
            copyright = copyright,
            date = date,
            explanation = explanation,
            hdUrl = hdUrl,
            mediaType = mediaType,
            serviceVersion = serviceVersion,
            title = title,
            url = ""
        )
        result shouldBeEqualTo expected
    }
}
