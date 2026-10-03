package com.example.faidalib.utility_managers

import com.example.faidalib.backend.PayloadDump
import com.example.faidalib.helpers.InputManager
import com.example.faidalib.models.SalesItem
import com.example.faidalib.models.RecordData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecordsManager(private val inputManager: InputManager, private val payloadDump: PayloadDump) {
    private val json = Json{
        ignoreUnknownKeys = true
    }
    fun createDailyRecord(){
        println("Creating a new day's record...")
        val dateToday =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        val itemsSold = mutableListOf<SalesItem>()

        while (true){
            println("Please enter information about items you sold today")
            val itemName = inputManager.promptStringInput("Item name: ")
            val itemQtySold = inputManager.promptIntInput("$itemName sold: ")
            val itemSellingPrice = inputManager.promptDoubleInput("Item selling price: ")

            itemsSold.add(SalesItem(itemName, itemQtySold, itemSellingPrice))

            val proceed = inputManager.promptStringInput("Do you want to add another item?: ")
            if (proceed.lowercase().trim() != "y") break
        }
        // Collect other low-key preliminary fields
        val timeIn = inputManager.promptStringInput("Time clocked in: ")
        val timeOut = inputManager.promptStringInput("Time clocked out: ")
        val maleCustomers = inputManager.promptIntInput("Male customers: ")
        val femaleCustomers = inputManager.promptIntInput("Female customers: ")
        val merchantComments = inputManager.promptStringInput("Comments: ")

        // Assemble the data, encoding the item data sold to a JSON
        val itemDataJSONPayload = json.encodeToString(itemsSold)

        val recordData = RecordData(dateToday, timeIn, timeOut, maleCustomers,
            femaleCustomers, merchantComments)
        val recordCreationResult = payloadDump.sendRecordsCreationRequestPayload(
            recordData, itemDataJSONPayload
        )
        print(recordCreationResult)
    }
}