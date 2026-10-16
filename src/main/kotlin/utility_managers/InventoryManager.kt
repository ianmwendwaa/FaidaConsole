package com.example.faidalib.utility_managers

import com.example.faidalib.helpers.InlineHelper
import com.example.faidalib.helpers.InlineInventoryHelper
import com.example.faidalib.payload_dump.PayloadDump
import com.example.faidalib.helpers.InputManager
import com.example.faidalib.models.ItemDataOutput
import com.example.faidalib.models.NewItemData
import kotlinx.serialization.json.Json

class InventoryManager(private val inputManager: InputManager, private val payloadDump: PayloadDump) {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun createItem(){
        println("Initializing database engine...")
        println("Ready! Provide item details when ready.")

        val itemName = inputManager.promptStringInput("Item name: ")
        val itemCategory = inputManager.promptStringInput("Item category: ")
        val itemBP = inputManager.promptDoubleInput("Item buying price: ")
        val itemQty = inputManager.promptIntInput("Item quantity: ")
        val dateToday = inputManager.dateToday()

        // Generate this item's creation signature. Refer to function generateItemUniqueSignature()


        //Empty list to store the signature
        val itemIdSignature = mutableListOf<String>()

        // Get the first two characters in the item's name
        val ftChars = itemName.substring(0, 2)

        // Validate the date provided
        val parsedDate = InlineInventoryHelper.validateDate(dateToday) ?: return


        val data = NewItemData(itemName, itemCategory, itemBP, itemQty, dateToday)
        val creationResult = payloadDump.sendItemCreationRequestPayload(data)
        print(creationResult)
    }

    fun deleteItem(){
        // Minute fix: Display the items available to make the menu safer and more user-friendly.
        viewItemsInStock()
        println("What item do you wish to delete?: ")
        val redItem = inputManager.promptStringInput("Item name: ")

        val deletionResult = payloadDump.sendItemDeletionRequest(redItem)
        print(deletionResult)
        viewItemsInStock()
    }

    fun viewItemsInStock(){
        println("Connecting to the database...")

        // Initiate contact with the database via the script
        val rawRowsOutput = payloadDump.commitViewItemsRequest()

        try {
            val itemsListRetrieved: List<ItemDataOutput> = json.decodeFromString(rawRowsOutput)
            println("-".repeat(74))
            println(String.format("%-5s | %-12s | %-10s | %-10s | %-8s | %-10s", "ID", "NAME", "CATEGORY", "BUYING_PRICE", "QTY", "ARRIVAL_DATE"))
            println("-".repeat(74))

            // Append the items into a tabular view
            for (item in itemsListRetrieved){
                println(String.format("%-5d | %-12s | %-10s | %-12.2f | %-8d | %-12s" ,
                    item.product_id,
                    item.product_name.trim(),
                    item.product_category.trim(),
                    item.buying_price,
                    item.product_quantity,
                    item.arrival_date
                ))
            }
        }catch (e: Exception){
            println("Error parsing rows into JSON format. Reason:\n ${e.message}")
            println("Showing raw output instead: $rawRowsOutput")
        }
    }

    fun stockOverView(){
        println("Providing stock overview...")
    }
}