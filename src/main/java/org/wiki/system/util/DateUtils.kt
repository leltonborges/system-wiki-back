package org.wiki.system.util

import java.time.YearMonth
import java.time.format.DateTimeFormatter

val YearMonth_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM")
val formatYearMonth: (YearMonth) -> Int = { yearMonth -> yearMonth.format(YearMonth_FORMATTER).toInt() }
