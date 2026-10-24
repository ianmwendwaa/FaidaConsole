package com.example.faidalib.bridge

import java.io.File

class PythonProtoBridge: PythonBridge {
    override fun runPyScript(scriptName: String, action: String, args: List<String>): String {
        // Cross-platform compatibility (assuming python's installed)
        val pyExecutable = if (System.getProperty("os.name").contains("Win", ignoreCase = true)){
            "./.venv/Scripts/python.exe"
        }else {
            ".venv/bin/python"
        }
        val cmd = mutableListOf(pyExecutable, "${scriptName}.py", action)

        val obfuscateArgs = args.map { arg ->
            if(arg.startsWith("[") && arg.endsWith("]") || arg.startsWith("{") && arg.endsWith("}")){
                "\"${arg.replace("\"", "\\\"")}\""
            }else{
                arg
            }
        }
        cmd.addAll(obfuscateArgs)
        val process = ProcessBuilder(cmd).directory(File("py")).start()
        val error = process.errorStream.bufferedReader().readText()
        if(error.isNotEmpty()) println("Encountered error while executing. Reason:\n $error")
        return process.inputStream.bufferedReader().readText()
    }
}