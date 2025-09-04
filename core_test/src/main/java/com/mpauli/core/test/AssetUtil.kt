package com.mpauli.core.test

import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

object AssetUtil {

    @Throws(IOException::class)
    fun getStringFromFile(path: String?): String {
        val requiredPath = requireNotNull(path) { "path must not be null" }
        val stream = AssetUtil::class.java.classLoader?.getResourceAsStream(requiredPath)
            ?: throw FileNotFoundException("Resource not found: $requiredPath")

        return BufferedReader(InputStreamReader(stream, StandardCharsets.UTF_8)).use { it.readText() }
    }
}
