package com.mpauli.core.apodapi.data

import com.mpauli.core.models.Apod
import com.mpauli.core.network.BuildConfig
import com.mpauli.core.network.client.KtorClientBuilder
import com.mpauli.core.test.mockFromFile
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockWebServer
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate

internal class ApodApiTest {

    private val server = MockWebServer()

    @BeforeEach
    fun setUp() {
        server.start()
    }

    @AfterEach
    fun tearDown() {
        server.close()
    }

    @Test
    fun `should parse correctly getApod`() = runTest {
        // Given
        val date = LocalDate.parse("2025-08-27")

        server.mockFromFile("apod-response.json")

        val client = HttpClient(CIO) {
            install(ContentNegotiation) { json() }
        }

        val builder: KtorClientBuilder = mock()
        whenever(builder.buildKtorClient()) doReturn client

        val mockBaseUrl = server.url("/").toString()

        val apodApi = ApodApi(
            ktorClientBuilder = builder,
            baseUrl = mockBaseUrl
        )

        val expected = Apod(
            copyright = "Panther Observatory",
            date = date,
            explanation = "In this stunning cosmic vista, galaxy M81 is...",
            hdUrl = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c80.jpg",
            mediaType = "image",
            serviceVersion = "v1",
            title = "Galaxy Wars: M81 versus M82",
            url = "https://apod.nasa.gov/apod/image/0604/M81_M82_schedler_c25.jpg"
        )

        // When
        val result = apodApi.getApod(date)

        // Then
        result shouldBeEqualTo expected

        val recorded = server.takeRequest()
        recorded.url.queryParameter("date") shouldBeEqualTo date.toString()
        recorded.url.queryParameter("api_key") shouldBeEqualTo BuildConfig.NASA_API_KEY
    }
}
