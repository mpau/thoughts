package com.mpauli.core.test

import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.Headers.Companion.headersOf

fun MockWebServer.mockFromFile(path: String, code: Int = 200) {
    enqueue(
        MockResponse(
            code = code,
            headers = headersOf("Content-Type", "application/json"),
            body = AssetUtil.getStringFromFile(path)
        )
    )
}
