package com.example.faidalib

data class ItemData(val itemName: String, val itemCategory: String, val itemBP: String, val itemQty: String)
class MenuOptions: PythonBridge {
    override fun runPyScript(scriptName: String, args: List<String>): String {
        val commands = mutableListOf("./.venv/Scripts/python.exe","${scriptName}.py")
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
        print("Item Buying price: ")
        val itemBP = readln()
        print("Item category: ")
        val itemCategory = readln()
        print("Item Quantity: ")
        val itemQty = readln()
        print(runPyScript("create_item", listOf(itemName, itemCategory, itemBP, itemQty)))
    }

    fun viewItems(){
        println("Fetching available items...")
        runPyScript("database_worker", listOf())
    }

    fun deleteItem() {
        print("Enter the name of the item you want to delete: ")
        val redItem = readln()
        runPyScript("delete_item", listOf(redItem))
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