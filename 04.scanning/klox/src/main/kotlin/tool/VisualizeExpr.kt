package tool

import Expr
import Token
import printers.TreantJsPrinter
import java.io.File
import kotlin.system.exitProcess

class VisualizeExpr {
    fun toTreantJson(exprString: String, outputFile: File) {
        val printer = TreantJsPrinter()
        //TODO: parse exprString
        val expr = genSampleExpr()
        val nodeStructureString = printer.toJson(expr)

        val treantJs =
            "let config = { chart: { container:'#chart' }, nodeStructure: $nodeStructureString };let chart = new Treant(config);"

        outputFile.writeText(treantJs)
    }
}

fun genSampleExpr(): Expr {
    val fortyFive = Expr.Literal(45.67)
    val grouping = Expr.Grouping(fortyFive)
    val star = Token(TokenType.STAR, "*", null, 1)
    val oneHundredTwentyThree = Expr.Literal(123)
    val minus = Token(TokenType.MINUS, "-", null, 1)
    val unary = Expr.Unary(minus, oneHundredTwentyThree)

    return Expr.Binary(unary, star, grouping)
}

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Usage: VisualizeExpr <output file>")
        exitProcess(1)
    }
    val visualizer = VisualizeExpr()
    val outputFile = File(args.first())

    visualizer.toTreantJson("TODO", outputFile)
}