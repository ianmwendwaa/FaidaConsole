package com.example.faidalib.bridge

interface PythonBridge {
    fun runPyScript(scriptName: String, action: String, args: List<String>): String
}
