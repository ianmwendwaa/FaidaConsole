package com.example.faidalib.bridge

class PythonProtoBridge: PythonBridge {
    override fun runPyScript(scriptName: String, action: String, args: List<String>): String {
        val cmd = mutableListOf("./.venv/Scripts/python.exe", "${scriptName}.py", action)

        val obfuscateArgs = args.map { arg ->
            if(arg.startsWith("[") && arg.endsWith("]") || arg.startsWith("{") && arg.endsWith("}")){
                "\"${arg.replace("\"", "\\\"")}\""
            }else{
                arg
            }
        }
        cmd.addAll(obfuscateArgs)
        val process = ProcessBuilder(cmd).start()
        val error = process.errorStream.bufferedReader().readText()
        if(error.isNotEmpty()) println("Encountered error while executing. Reason:\n $error")
        return process.inputStream.bufferedReader().readText()
    }
}