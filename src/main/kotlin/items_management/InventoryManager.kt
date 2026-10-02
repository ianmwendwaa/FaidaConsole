package com.example.faidalib.items_management

import com.example.faidalib.ItemDataOutput
import com.example.faidalib.PythonBridge
import kotlinx.serialization.json.Json

class InventoryManager(private val bridge: PythonBridge){
    private val inventoryScriptName = "inventory_app"

    fun createItem(itemName: String, itemCategory: String, itemBP: String, itemQty: String): String{
        return bridge.runPyScript(inventoryScriptName, "create_item", listOf(
            itemName, itemCategory, itemBP, itemQty)
        )
    }
    fun deleteItem(itemName: String): String{
        return bridge.runPyScript(inventoryScriptName, "delete_item", listOf(itemName))
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun viewItems(){
        println("Fetching items in inventory...")
        val rawRows = bridge.runPyScript(inventoryScriptName, "view_items", emptyList())

        try {
            //Print in the format: id, name, category, bp, qty
            val items: List<ItemDataOutput> = json.decodeFromString(rawRows)
            println("-".repeat(55))
            println(String.format("%-5s | %-12s | %-10s | %-10s | %-8s", "ID", "NAME", "CATEGORY", "BUYING_PRICE", "QTY"))
            println("-".repeat(55))

            for (item in items){
                println(String.format("%-5d | %-12s | %-10s | %-10.2f | %-8d",
                    item.product_id,
                    item.product_name.trim(),
                    item.product_category.trim(),
                    item.buying_price,
                    item.product_quantity))
            }
        }catch (e: Exception){
            println("Error parsing JSON output. Reason: ${e.message}. Logging raw JSON data instead...")
            println(rawRows)
        }
    }
}