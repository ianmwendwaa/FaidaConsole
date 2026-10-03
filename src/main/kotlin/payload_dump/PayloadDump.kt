package com.example.faidalib.backend

import com.example.faidalib.bridge.PythonBridge
import com.example.faidalib.models.NewItemData
import com.example.faidalib.models.RecordData

class PayloadDump(private val bridge: PythonBridge) {
    private val inventoryScript = "inventory_app"
    private val recordsScript = "daily_records_app"

    /* Item payload dumps*/
    fun sendItemCreationRequestPayload(newItemData: NewItemData): String{
        print(newItemData)
        return bridge.runPyScript(inventoryScript, "create_item", listOf(
            newItemData.itemName, newItemData.itemBP.toString(), newItemData.itemTotalQty.toString()
        ))
    }
    fun sendItemDeletionRequest(itemName: String): String{
        return bridge.runPyScript(inventoryScript, "delete_item", listOf(itemName))
    }
    fun commitViewItemsRequest(): String{
        return bridge.runPyScript(inventoryScript, "view_items", emptyList())
    }

    /* Record payload dumps*/
    fun sendRecordsCreationRequestPayload(recordData: RecordData, jsonPayload: String): String{
        return bridge.runPyScript(recordsScript, "make_record_entry", listOf(
            recordData.recordDate, recordData.timeClockedIn, recordData.timeClockedOut,
            recordData.maleCustomers.toString(), recordData.femaleCustomers.toString(), recordData.comments,
            jsonPayload
        ))
    }

}