package com.example.faidalib

import com.example.faidalib.items_management.InventoryManager
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun main() {
//    val faidaRunning = true
//    while (faidaRunning){
//        val userChoice = readln()
//
//        if (userChoice.length > 1 || userChoice.isEmpty()){
//            println("Please enter a valid choice!")
//        }
//        when(userChoice){
//            "1" -> menuActions.createNewItem()
//            "2" -> menuActions.viewItems()
//            "3" -> menuActions.deleteItem()
//            "4" -> menuActions.stockOverView()
//            "5" -> menuActions.viewDailyPerformance()
//            "6" -> menuActions.createDailyRecord()
//            "7" -> !faidaRunning
//        }
//    }
    val bridge = PythonProtoBridge()
    val inventoryManager = InventoryManager(bridge)
    val menuManager = MenuManager(inventoryManager)

    menuManager.startFaidaEngine()
}