package com.example.faidalib

import com.example.faidalib.backend.PayloadDump
import com.example.faidalib.bridge.PythonProtoBridge
import com.example.faidalib.helpers.InputManager
import com.example.faidalib.helpers.MenuManager
import com.example.faidalib.utility_managers.InventoryManager
import com.example.faidalib.utility_managers.RecordsManager

fun main() {
    val bridge = PythonProtoBridge()
    val payloadDump = PayloadDump(bridge)
    val recordsManager = RecordsManager(InputManager(), payloadDump)
    val inventoryManager = InventoryManager(InputManager(), payloadDump)
    val menuManager = MenuManager(inputManager = InputManager(), inventoryManager, recordsManager)
    menuManager.startFaidaEngine()
}