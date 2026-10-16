package com.example.faidalib.helpers

import com.example.faidalib.models.NewItemData

data class ParsedDateFormat(val year: String, val month: String, val date: String)

// Contains helper functions relevant to InventoryManager.kt
object InlineInventoryHelper {
    fun validateDate(dateStr: String): ParsedDateFormat? {
        // Expect date format like yyyy-MM-dd in regard to MySQL standards
        val parsedDate: ParsedDateFormat
        val expectedDateFormat = """^(?<year>\d{4})-(?<month>\d{2})-(?<date>\d{2})$""".toRegex()

        if (!expectedDateFormat.matches(dateStr)) {
            println(
                "Provided date: $dateStr does not match the predefined format yyyy-MM-dd.\nFix: " +
                        "Make sure the provided date matches <yyyy-MM-dd>"
            )
            return null
        }
        expectedDateFormat.matchEntire(dateStr).let{ matchResult ->
            val year = matchResult?.groups["year"]?.value ?: "yyyy"
            val month = matchResult?.groups["month"]?.value ?: "MM"
            val date = matchResult?.groups["date"]?.value ?: "dd"

            parsedDate = ParsedDateFormat(year, month, date)
        }
        return parsedDate
    }
    fun generateUniqueItemSignature(itemCreating: NewItemData): String{

        return ""
    }
}