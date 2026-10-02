package com.example.faidalib

class PythonProtoBridge: PythonBridge {
    override fun runPyScript(scriptName: String, action: String, args: List<String>): String {
        val cmd = mutableListOf("./.venv/Scripts/python.exe", "${scriptName}.py", action)
        cmd.addAll(args)

        val process = ProcessBuilder(cmd).start()
        val error = process.errorStream.bufferedReader().readText()

        if(error.isNotEmpty()) println("Encountered error while executing. Reason? $error")
        return process.inputStream.bufferedReader().readText()
    }
}