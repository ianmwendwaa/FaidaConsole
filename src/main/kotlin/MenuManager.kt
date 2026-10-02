package com.example.faidalib

import com.example.faidalib.items_management.InventoryManager
import java.awt.Choice
import kotlin.io.println

class MenuManager(private val menuChoice: String) {
    fun populateMenu(): List<String>{
        val menuList = listOf(
            "1. Create new item entry",
            "2. View item inventory",
            "3. Delete item from inventory",
            "4. Stock overview",
            "5. View daily performance",
            "6. Create a daily record",
            "7. Exit"
        )
        return menuList
    }
    fun startFaidaEngine(){
        val bridge = PythonProtoBridge()
        val inventoryManager = InventoryManager(bridge)
        println(populateMenu().joinToString("\n"))
        when(menuChoice){
            "1" -> inventoryManager.createItem("Grapes", "Fruit", 5200.00, 140)
        }
    }
}

fun main(){
    MenuManager("1").startFaidaEngine()
}