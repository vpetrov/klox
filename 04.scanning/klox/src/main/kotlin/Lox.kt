import java.io.File

object Lox {
    var hadError = false
    private set

    fun runFile(path: String) {
        println("Running file at $path")
        val text = File(path).readText()
        run(text)
    }

    fun runPrompt() {
        println("REPL")
        while (true) {
            val line = readLine() ?: break
            run(line)
            hadError = false
        }
    }

    private fun run(source: String) {
        println("running")
        val scanner = Scanner(source)
        val tokens = scanner.scanTokens()

        tokens.forEach { token ->
            println(token)
        }
    }

    fun error(line:Int, message:String) = report(line, "", message)

    private fun report(line:Int, where:String, message:String) {
        println("[line $line] Error $where: $message")
        hadError = true
    }
}