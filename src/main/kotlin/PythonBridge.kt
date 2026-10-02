package com.example.faidalib

interface PythonBridge {
    fun runPyScript(scriptName: String, action: String, args: List<String>): String
}
