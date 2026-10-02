package com.example.faidalib.items_management

import com.example.faidalib.PythonBridge
import kotlin.math.sign

class InventoryManager(private val bridge: PythonBridge){
    private val inventoryScriptName = "inventory_app"

    fun createItem(itemName: String, itemCategory: String, itemBP: Double, itemQty: Int): String{
        println("Creating item $itemName")
        return bridge.runPyScript(inventoryScriptName, "create_item", listOf(
            itemName, itemCategory, itemBP.toString(), itemQty.toString())
        )
    }
    fun deleteItem(itemName: String){

    }
}