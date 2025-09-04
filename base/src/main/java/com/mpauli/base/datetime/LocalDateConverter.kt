package com.mpauli.base.datetime

import androidx.room.TypeConverter
import java.time.LocalDate

object LocalDateConverter {

    @TypeConverter
    fun fromEpochDay(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun toEpochDay(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}
