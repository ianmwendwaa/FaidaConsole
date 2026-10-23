package com.example.faidalib.utility_managers

import com.example.faidalib.payload_dump.PayloadDump
import com.example.faidalib.helpers.InputManager
import com.example.faidalib.models.SalesItem
import com.example.faidalib.models.RecordData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RecordsManager(private val inputManager: InputManager, private val payloadDump: PayloadDump, private val inventoryManager: InventoryManager) {
    private val json = Json{
        ignoreUnknownKeys = true
    }

    /* Creation of a weekly record */
    fun startWeekRecord(){
        // Starts a new week (typically 7 days of the week) and dynamically adds records as new records are created
        // If no active week is active, prompt the creation of a week

    }
    fun createDailyRecord(){
        val itemsSold = mutableListOf<SalesItem>()

        val salesItem = SalesItem("Oranges", 5, 35.00, "OR26-0501-195F-6301")
        itemsSold.add(salesItem)

        val recordData = RecordData(
            "08/05/2026",
            "13:47",
            "22:02",
            1,
            2,
            "..."
        )
        println(payloadDump.sendRecordsCreationRequestPayload(recordData, json.encodeToString(itemsSold)))
//        while (true) {
            println("Creating a new day's record...")
//        val dateToday = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
//            val dateToday = inputManager.promptStringInput("Record's date: ")
//            val itemsSold = mutableListOf<SalesItem>()

//            while (true){
//                println("Please enter information about items you sold today. Here's a list of items: ")
////                inventoryManager.viewItemsInStock()
//                // What if, instead of prompting blindly for the item name, the program displays a list of items that are
//                // in the database, based off the generated id signature?
//                val itemName = inputManager.promptStringInput("Item name: ")
//                val itemQtySold = inputManager.promptIntInput("$itemName(s) sold: ")
//                val itemSellingPrice = inputManager.promptDoubleInput("Item selling price: ")
//                val itemSignature = inputManager.promptStringInput("Item signature: ")
//
//                itemsSold.add(SalesItem(itemName, itemQtySold, itemSellingPrice, itemSignature))
//
//                val proceed = inputManager.promptStringInput("Do you want to add another item?: ")
//                if (proceed.lowercase().trim() != "y") break
//            }
//            // Collect other low-key preliminary fields
//            val timeIn = inputManager.promptStringInput("Time clocked in: ")
//            val timeOut = inputManager.promptStringInput("Time clocked out: ")
//            val maleCustomers = inputManager.promptIntInput("Male customers: ")
//            val femaleCustomers = inputManager.promptIntInput("Female customers: ")
//            val merchantComments = inputManager.promptStringInput("Comments: ")
//
//            // Assemble the data, encoding the item data sold to a JSON
//            val itemDataJSONPayload = json.encodeToString(itemsSold)
//
//            val recordData = RecordData(dateToday, timeIn, timeOut, maleCustomers,
//                femaleCustomers, merchantComments)
//            payloadDump.sendRecordsCreationRequestPayload(
//                recordData, itemDataJSONPayload
//            )
//            val wanna_continue = inputManager.promptStringInput("Do you wish to continue adding records: ")
//            if (wanna_continue.trim().lowercase() != "y") break
//        }
//        }
    }
    fun viewDailyPerformance(){
        val requestResult = payloadDump.requestDailyPerformanceGraph()
        print(requestResult)
    }
}