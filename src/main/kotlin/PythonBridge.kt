package com.example.faidalib

interface PythonBridge {
    fun runPyScript(scriptName: String, args: List<String>): String
}