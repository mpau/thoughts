package com.mpauli.base.datetime

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.toStringWithFormat(): String = format(DateHelper.formatter)

private object DateHelper {

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
}
