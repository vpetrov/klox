import kotlin.system.exitProcess

private const val EX_USAGE = 64
private const val EX_DATAERR = 65

fun main(args: Array<String>) {
    if (args.size > 1) {
        showUsage(args[0])
        exitProcess(EX_USAGE)
    }

    if (args.size == 1) {
        Lox.runFile(args[0])
    } else {
        Lox.runPrompt()
    }

    if (Lox.hadError) {
        exitProcess(EX_DATAERR)
    }
}

fun showUsage(programName: String) = println("Usage: $programName [script]")