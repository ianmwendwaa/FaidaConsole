package com.example.faidalib.logger

import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val logDateTime: ()-> String = {
    "[DUMP] " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - "
}
private val logFile = File("src/main/kotlin/logger", "log_dump.txt")

fun dumpOutputLog(logData: List<Any>){
    // Create the text file if it does not exist
    logFile.parentFile?.mkdirs()

    FileOutputStream(logFile,true).bufferedWriter().use { writer ->
        writer.append(
            logDateTime.toString()
        )
        writer.append(logData.joinToString(""))
        writer.newLine()
    }
}
fun flushLog(){
    FileOutputStream(logFile).bufferedWriter().use { writer ->
        writer.flush()
    }
}