package com.example.faidalib.logger

fun gambleOurFate(){
    val venues = listOf("TRM", "GCM")
    println("Buckle up Shanaya, we are going to: ${venues.random()}")
}

fun main() {
    gambleOurFate()
}