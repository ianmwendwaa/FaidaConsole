package com.example.faidalib.utility_managers

import com.example.faidalib.helpers.InlineInventoryHelper
import com.example.faidalib.payload_dump.PayloadDump
import com.example.faidalib.helpers.InputManager
import com.example.faidalib.logger.dumpOutputLog
import com.example.faidalib.models.ItemDataOutput
import com.example.faidalib.models.NewItemData
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

class InventoryManager(private val inputManager: InputManager, private val payloadDump: PayloadDump) {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    fun createItem(){
        dumpOutputLog(listOf("User picked option 1: Create item"))
        println("Initializing database engine...")
//        println("Ready! Provide item details when ready.")

        val itemName = inputManager.promptStringInput("Item name: ")
        val itemCategory = inputManager.promptStringInput("Item category: ")
        val itemBP = inputManager.promptDoubleInput("Item buying price: ")
        val itemQty = inputManager.promptIntInput("Item quantity: ")
        val dateToday = inputManager.dateToday()

        val data = NewItemData(itemName, itemCategory, itemBP, itemQty, "2026-05-01")

        // Generate this item's creation signature. Refer to function generateItemUniqueSignature()
        val generated_item_signature = InlineInventoryHelper.generateUniqueItemSignature(data)
        val creationResult = payloadDump.sendItemCreationRequestPayload(data, generated_item_signature)
        print(creationResult)
        dumpOutputLog(listOf("User attempted to created item: $data with signature $generated_item_signature" +
                " which yielded result: $creationResult"))
    }

    fun deleteItem(){
        // Minute fix: Display the items available to make the menu safer (prevent accidental deletion) and more user-friendly.
        viewItemsInStock()
        println("What item do you wish to delete?: ")
        val redItemSignature = inputManager.promptStringInput("Item signature: ")

        val deletionResult = payloadDump.sendItemDeletionRequest(redItemSignature)
        print(deletionResult)
        viewItemsInStock()
    }

    fun viewItemsInStock(){
        println("Connecting to the database...")
        dumpOutputLog(listOf("User requested to view items in stock."))

        // Initiate contact with the database via the script
        val rawRowsOutput = payloadDump.commitViewItemsRequest()

        try {
            val itemsListRetrieved: List<ItemDataOutput> = json.decodeFromString(rawRowsOutput)
            println("-".repeat(91))
            println(String.format("%-5s | %-12s | %-10s | %-10s | %-8s | %-10s | %-25s",
                "ID", "NAME", "CATEGORY", "BUYING_PRICE", "QTY", "ARRIVAL_DATE", "ITEM_SIGNATURE"))
            println("-".repeat(91))

            // Append the items into a tabular view
            for (item in itemsListRetrieved){
                println(String.format("%-5d | %-12s | %-10s | %-12.2f | %-8d | %-12s | %-25s" ,
                    item.product_id,
                    item.product_name.trim(),
                    item.product_category.trim(),
                    item.buying_price,
                    item.product_quantity,
                    item.arrival_date,
                    item.generated_item_signature ?: "missing_signature"
                ))
            }
        }catch (e: Exception){
            dumpOutputLog(listOf("Encountered an error while viewing items in stock. Reason: ", { e.message }))
            println("Error parsing rows into JSON format. Reason:\n ${e.message}")
            println("Showing raw output instead: $rawRowsOutput")
        }
    }

    fun stockOverView(){
        println("Providing stock overview...")
    }
}