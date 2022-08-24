package klox.cmd

import klox.Lox
import java.io.File
import kotlin.system.exitProcess

private const val EX_USAGE = 64
private const val EX_DATAERR = 65

fun main(args: Array<String>) {
    if (args.size > 1) {
        showUsage(args[0])
        exitProcess(EX_USAGE)
    }

    if (args.size == 1) {
        runFile(args[0])
    } else {
        runPrompt()
    }

    if (Lox.error) {
        exitProcess(EX_DATAERR)
    }
}

fun showUsage(programName: String) = println("Usage: $programName [script]")

fun runFile(path: String) {
    println("Running file at $path")
    val text = File(path).readText()
    Lox.run(text)
}

fun runPrompt() {
    println("REPL")
    while (true) {
        val line = readLine() ?: break
        Lox.run(line)
        Lox.error = false
    }
}