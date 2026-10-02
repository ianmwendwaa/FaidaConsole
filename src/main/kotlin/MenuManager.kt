package com.example.faidalib

import com.example.faidalib.items_management.InventoryManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MenuManager(private val inventoryManager: InventoryManager) {
    private fun populateMenu(): List<String>{
        val menuList = listOf(
            "1. Create new item entry",
            "2. View item inventory",
            "3. Delete item from inventory",
            "4. Stock overview",
            "5. View daily performance",
            "6. Create a daily record",
            "7. Delete a record"
        )
        return menuList
    }

    fun startFaidaEngine(){
        println("-".repeat(55))
        println("\t Welcome to Faida+".trim())
        println("-".repeat(55))
        println(populateMenu().joinToString("\n"))
        print("Select an action: ")
        val menuChoice = readln()

        when(menuChoice){
            "1" -> _invoke_create_item()
            "2" -> _invoke_view_items()
            "3" -> _invoke_delete_item()
            "4" -> _invoke_stock_overview()
            "5" -> _invoke_daily_performance()
            "6" -> _invoke_create_daily_record()
            "7" -> _invoke_delete_record()
        }
    }

    private fun _invoke_create_item(){
        println("Provide item's details: ")
        print("Item Name: ")
        val itemName = readln()
        print("Item category: ")
        val itemCategory = readln()
        print("Item Buying price: ")
        val itemBP = readln()
        print("Item Quantity: ")
        val itemQty = readln()

        val itemCreationResult = inventoryManager.createItem(itemName, itemCategory, itemBP, itemQty)
        print(itemCreationResult)
    }
    private fun _invoke_delete_item(){
        print("Enter the name of the item you wish to delete: ")
        val redItem = readln()
        if (redItem.isEmpty()){
            println("Please provide a valid item name!")
            return
        }
        inventoryManager.deleteItem(redItem)
    }
    private fun _invoke_view_items(){
        inventoryManager.viewItems()
    }
    private fun _invoke_create_daily_record(){
        // Collects the record's date, time clocked in&out, item sold & the selling price, male & female customers, comments
        val dateToday = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    }

    private fun _invoke_stock_overview(){
        println("Invoking Faida's graph engine to provide stock overview")
    }

    private fun _invoke_daily_performance(){
        println("Invoking Faida's graph engine to show daily performance")
    }

    private fun _invoke_delete_record(){
        println("Deleting record")
    }
}
