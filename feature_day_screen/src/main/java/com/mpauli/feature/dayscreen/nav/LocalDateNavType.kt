package com.mpauli.feature.dayscreen.nav

import android.os.Bundle
import androidx.navigation.NavType
import java.time.LocalDate

internal object LocalDateNavType : NavType<LocalDate>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): LocalDate? {
        return bundle.getString(key)?.let { LocalDate.parse(it) }
    }

    override fun parseValue(value: String): LocalDate {
        return LocalDate.parse(value)
    }

    override fun put(bundle: Bundle, key: String, value: LocalDate) {
        bundle.putString(key, value.toString())
    }
}
