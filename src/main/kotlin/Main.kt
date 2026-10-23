package com.example.faidalib

import com.example.faidalib.payload_dump.PayloadDump
import com.example.faidalib.bridge.PythonProtoBridge
import com.example.faidalib.helpers.InputManager
import com.example.faidalib.helpers.MenuManager
import com.example.faidalib.logger.dumpOutputLog
import com.example.faidalib.utility_managers.InventoryManager
import com.example.faidalib.utility_managers.RecordsManager

// Try 1 Ap-2026-10-16-180-Fruit-2585
// Try 2 IT-2026-10-16-180-F-6600
// Try 3 IT26-1016-099F-7715
fun main() {
    val bridge = PythonProtoBridge()
    val payloadDump = PayloadDump(bridge)
    val inventoryManager = InventoryManager(InputManager(), payloadDump)
    val recordsManager = RecordsManager(InputManager(), payloadDump, inventoryManager)
    val menuManager = MenuManager(inputManager = InputManager(), inventoryManager, recordsManager)
    menuManager.startFaidaEngine()
//    inventoryManager.viewItemsInStock()
}