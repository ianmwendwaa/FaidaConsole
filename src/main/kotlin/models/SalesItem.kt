package com.example.faidalib.models

import kotlinx.serialization.Serializable

@Serializable
data class SalesItem(val name: String, val qtySold: Int, val sellingPrice: Double)