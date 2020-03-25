package com.arainko.nawts.view

import com.joestelmach.natty.Parser
import kotlinx.android.synthetic.main.fragment_edit.*
import org.apache.commons.validator.GenericValidator
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter

object DateInferer {
    fun inferEpochDate(text: String): Long {
        val normalizedText = text
            .replace("-", "/")
            .replace(".", "/")


        val europeanRegex = "[0-9]{2}[/][0-9]{2}[/][0-9]{4}".toRegex()
        val europeanMatch = europeanRegex.find(normalizedText)
        val inferredDates = Parser().parse(normalizedText)
        val now = Instant.now()
        val zone = ZoneId.systemDefault()
        val zoneOffset = zone.rules.getOffset(now)

        return when {
            europeanMatch != null && GenericValidator.isDate(europeanMatch.value, "dd/MM/yyyy", true) ->
                LocalDate.parse(europeanMatch.value, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    .atTime(12, 0)
                    .toInstant(zoneOffset)
                    .toEpochMilli()
            inferredDates != null && inferredDates.isNotEmpty() ->
                inferredDates[0].dates[0].time
            else -> now.toEpochMilli()
        }
    }
}