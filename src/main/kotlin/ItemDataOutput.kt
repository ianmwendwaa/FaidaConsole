package com.example.faidalib

import kotlinx.serialization.Serializable

@Serializable
data class ItemDataOutput(
    val product_id: Int,
    val product_name: String,
    val product_category: String,
    val buying_price: Double,
    val product_quantity: Int
)