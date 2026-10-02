package com.example.faidalib

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ItemDataOutput(
    val product_id: Int,
    val product_name: String,
    val product_category: String,
    val buying_price: Double,
    val product_quantity: Int
)
class MenuOptions: PythonBridge {
    private val inventoryScriptName = "inventory_app"
    override fun runPyScript(scriptName: String, action: String, args: List<String>): String {
        val commands = mutableListOf("./.venv/Scripts/python.exe","${scriptName}.py", action)
        commands.addAll(args)

        val process = ProcessBuilder(commands).start()
        val error = process.errorStream.bufferedReader().readText()

        if(error.isNotEmpty()){
            println("Encountered error: $error")
        }
        return process.inputStream.bufferedReader().readText().trim()
    }

    fun createNewItem(){
        println("Provide item's details: ")
        print("Item Name: ")
        val itemName = readln()
        print("Item category: ")
        val itemCategory = readln()
        print("Item Buying price: ")
        val itemBP = readln()
        print("Item Quantity: ")
        val itemQty = readln()
        print(runPyScript(inventoryScriptName,"create_item", listOf(itemName, itemCategory, itemBP, itemQty)))
    }

    fun viewItems(){
        println("Fetching available items...")
        val rawItemRows = runPyScript(inventoryScriptName, "view_items" ,emptyList())
        try {
            val items: List<ItemDataOutput> = Json{
                ignoreUnknownKeys = true
            }.decodeFromString(rawItemRows)

            println(String.format("%-5s | %-12s | %-10s | %-10s | %-8s", "ID", "Name", "Category", "Price", "Qty"))
            println("-".repeat(55))

            for(item in items){
                println(String.format("%-5d | %-12s | %-10s | %-10.2f | %-8d",
                    item.product_id,
                    item.product_name.trim(),
                    item.product_category.trim(),
                    item.buying_price,
                    item.product_quantity))
            }
        }catch (e: Exception){
            println("Error parsing JSON output! Encountered problem: ${e.message}. Logging raw output instead")
            println(rawItemRows)
        }
    }

    fun deleteItem() {
        print("Enter the name of the item you want to delete: ")
        val redItem = readln()
        runPyScript(inventoryScriptName, "delete_item", listOf(redItem))
    }
    fun stockOverView() = println("Inventory in-stock in hindsight")
    fun viewDailyPerformance() = println("Appending daily recorded performance...")
    fun createDailyRecord() = println("Creating a day's record...")
}
fun main() {
    val menuActions = MenuOptions()
    println("Welcome to Faida+ Console.")
    println("1. Create new item entry")
    println("2. View item inventory")
    println("3. Delete item from inventory")
    println("4. Stock overview")
    println("5. View dailu performance")
    println("6. Create a daily record")
    println("7. Exit")
    print("How can I help you today?: ")

    val faidaRunning = true
    while (faidaRunning){
        val userChoice = readln()

        if (userChoice.length > 1 || userChoice.isEmpty()){
            println("Please enter a valid choice!")
        }
        when(userChoice){
            "1" -> menuActions.createNewItem()
            "2" -> menuActions.viewItems()
            "3" -> menuActions.deleteItem()
            "4" -> menuActions.stockOverView()
            "5" -> menuActions.viewDailyPerformance()
            "6" -> menuActions.createDailyRecord()
            "7" -> !faidaRunning
        }
    }
}