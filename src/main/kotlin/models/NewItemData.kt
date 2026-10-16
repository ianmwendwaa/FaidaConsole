package com.example.faidalib.models

import kotlinx.serialization.SerialName

data class NewItemData(
    @SerialName("item_name") val itemName: String,
    @SerialName("item_category") val itemCategory: String,
    @SerialName("item_bp") val itemBP: Double,
    @SerialName("item_total_qty") val itemTotalQty: Int,
    @SerialName("item_arrival_date") val itemArrivalDate: String
)
