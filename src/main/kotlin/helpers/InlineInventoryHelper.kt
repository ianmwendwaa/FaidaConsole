package com.example.faidalib.helpers

import com.example.faidalib.models.NewItemData
import java.util.Locale.getDefault
import kotlin.random.Random

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
            val year = matchResult?.groups["year"]?.value?.substring(2, 4) ?: "yyyy"
            val month = matchResult?.groups["month"]?.value ?: "MM"
            val date = matchResult?.groups["date"]?.value ?: "dd"

            parsedDate = ParsedDateFormat(year, month, date)
        }
        return parsedDate
    }
    fun generateUniqueItemSignature(itemCreating: NewItemData): String{
        /* I am thinking of creating a unique id for each item since I can't think of a simpler approach. I would use a
        * one-to-many relationship, but I want to prototype with this first.
        * In my mind, the signature matches something like: AP12-1026-180F-1234 -> Meaning:
        * AP - First two letters of the item.
        * 12 - The date purchased.
        * 10 - Month of purchase.
        * 26 - Year of purchase.
        * 180 - Quantity.
        * F - Category.
        * 1234 - Randomly generated values to cover a situation where two versions of the same
        * varieties of items have been brought. */

        // Container to hold the signature
        val uniqueSignature = mutableListOf<String>()

        // First two characters of the item's name
        val ft = itemCreating.itemName.substring(0, 2).uppercase(getDefault())

        // Obtain other params as aforementioned
        val parsedDate = this.validateDate(itemCreating.itemArrivalDate) ?:
            return "Error creating signature! Check date format"
        // Append a '0' to keep the signature's four-figured format per group
        val qty = if (itemCreating.itemTotalQty < 100) "0${itemCreating.itemTotalQty}" else itemCreating.itemTotalQty
        val categoryAbbr = itemCreating.itemCategory.substring(0, 1).uppercase(getDefault())

        val trailingSignatureValues = mutableListOf<Int>()
        repeat(4){
            val rand = Random.nextInt(0, 9)
            trailingSignatureValues.add(rand)
        }
        // Glue everything together
        uniqueSignature.add(ft)
        uniqueSignature.add(parsedDate.year)
        uniqueSignature.add("-")
        uniqueSignature.add(parsedDate.month)
        uniqueSignature.add(parsedDate.date)
        uniqueSignature.add("-")
        uniqueSignature.add(qty.toString())
        uniqueSignature.add(categoryAbbr)
        uniqueSignature.add("-")
        uniqueSignature.add(trailingSignatureValues.joinToString(""))

        return uniqueSignature.joinToString("")
    }
}