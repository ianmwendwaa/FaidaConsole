package com.example.faidalib.utility_managers

import com.example.faidalib.backend.PayloadDump
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
        val itemBP = inputManager.promptDoubleInput("Item buying price: ")
        val itemQty = inputManager.promptIntInput("Item quantity: ")

        val data = NewItemData(itemName, itemBP, itemQty)
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
    }

    fun viewItemsInStock(){
        println("Connecting to the database...")

        // Initiate contact with the database via the script
        val rawRowsOutput = payloadDump.commitViewItemsRequest()

        try {
            val itemsListRetrieved: List<ItemDataOutput> = json.decodeFromString(rawRowsOutput)
            println("-".repeat(55))
            println(String.format("%-5s | %-12s | %-10s | %-10s | %-8s", "ID", "NAME", "CATEGORY", "BUYING_PRICE", "QTY"))
            println("-".repeat(55))

            for (item in itemsListRetrieved){
                println(String.format("%-5d | %-12s | %-10s | %-10.2f | %-8d",
                    item.product_id,
                    item.product_name.trim(),
                    item.product_category.trim(),
                    item.buying_price,
                    item.product_quantity
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