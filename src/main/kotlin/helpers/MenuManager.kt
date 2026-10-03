package com.example.faidalib.helpers

import com.example.faidalib.models.SalesItem
import com.example.faidalib.models.RecordData
import com.example.faidalib.utility_managers.RecordsManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MenuManager(private val inputManager: InputManager,
                  private val inventoryManager: com.example.faidalib.utility_managers.InventoryManager,
                  private val recordManager: RecordsManager
) {
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
        val menuChoice = inputManager.promptStringInput("Select an action: ")

        when(menuChoice){
            "1" -> inventoryManager.createItem()
            "2" -> inventoryManager.viewItemsInStock()
            "3" -> inventoryManager.deleteItem()
            "4" -> inventoryManager.stockOverView()
            "5" -> _invoke_daily_performance()
            "6" -> recordManager.createDailyRecord()
            "7" -> _invoke_delete_record()
        }
    }

    private fun _invoke_daily_performance(){
        println("Invoking Faida's graph engine to show daily performance")
    }

    private fun _invoke_delete_record(){
        println("Deleting record")
    }
}