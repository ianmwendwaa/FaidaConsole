package com.example.faidalib.helpers

class InputManager {
    fun promptStringInput(message: String): String{
        print(message)
        val stringInput = readln()

        if(stringInput.isEmpty()){
            println("Please provide an input!")
        }
        return stringInput
    }
    fun promptDoubleInput(message: String): Double{
        print(message)
        val doubleInput = readln()

        if(doubleInput.isEmpty()){
            println("Please provide an input!")
        }
        return doubleInput.toDouble()
    }
    fun promptIntInput(message: String): Int{
        print(message)
        val intInput = readln()

        if(intInput.isEmpty()){
            println("Please provide an input!")
        }
        return intInput.toInt()
    }
}