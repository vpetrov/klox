package klox

import klox.printers.AstPrinter

object Lox {
    var error = false
    private val _errors = mutableListOf<String>()
    val errors = _errors as List<String>

    fun run(source: String) {
        println("running")
        val scanner = Scanner(source)
        val tokens = scanner.scanTokens()
        val parser = Parser(tokens)
        val expression = parser.parse()

        if (error || expression == null) return

        println(AstPrinter().print(expression))
    }

    fun error(line: Int, message: String) = report(line, "", message)

    fun error(token:Token, message:String) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message)
        } else {
            report(token.line, " at '${token.lexeme}'", message)
        }
    }

    private fun report(line: Int, where: String, message: String) {
        val e = "[line $line] Error $where: $message"
        _errors.add(e)
        println(e)
        error = true
    }

    fun parse(source: String): Expr? {
        val scanner = Scanner(source)
        val tokens = scanner.scanTokens()
        val parser = Parser(tokens)
        return parser.parse()
    }

    fun <R> print(expr:Expr, printer: Expr.Visitor<R>): R {
        return expr.accept(printer)
    }

    fun clearErrors() {
        error = false
        _errors.clear()
    }
}