package com.example.faidalib.models

data class RecordData(
    val recordDate: String,
    val timeClockedIn: String,
    val timeClockedOut: String,
    val maleCustomers: Int,
    val femaleCustomers: Int,
    val comments: String,
    val recordId: Int?=null
    )