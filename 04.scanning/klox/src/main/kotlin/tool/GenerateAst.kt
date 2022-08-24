package tool

import java.io.File
import java.io.PrintWriter
import kotlin.system.exitProcess

class GenerateAst {
    fun defineAst(outputDir: String, basename: String, types: List<String>) {
        val file = File("$outputDir/$basename.kt")
        val writer = file.printWriter()

        writer.use {
            it.println("abstract class $basename {")

            defineVisitor(writer, basename, types)

            types.forEach { type ->
                val className = type.split("=")[0].trim()
                val allFields = type.split("=")[1].trim()
                val fields = allFields.split(",")
                defineType(writer, basename, className, fields)
            }

            it.println()
            it.println("    abstract fun <R> accept(visitor:Visitor<R>):R")

            it.println("}")
        }
    }

    fun defineType(writer: PrintWriter, basename: String, className: String, fieldList: List<String>) {
        val valFields = fieldList.joinToString(prefix = "val ", separator = ", val")

        with (writer) {
            println("    data class $className($valFields) : Expr() {")

            println("        override fun <R> accept(visitor:Visitor<R>) = visitor.visit$className$basename(this)")
            println("    }")
        }
    }

    fun defineVisitor(writer:PrintWriter, basename:String, types:List<String>) {
        writer.println("    interface Visitor<R> {")

        types.forEach { type ->
            val typeName = type.split("=")[0].trim()
            writer.println("        fun visit$typeName$basename(${basename.lowercase()}:$typeName):R")
        }

        writer.println("    }")
    }
}

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Usage: generate_ast <output dir>")
        exitProcess(64)
    }

    val astGenerator = GenerateAst()

    val outputDir = args[0]
    astGenerator.defineAst(
        outputDir, "Expr", listOf(
            "Binary   = left:Expr, operator:Token, right:Expr",
            "Grouping = expression:Expr",
            "Literal  = value:Any",
            "Unary    = operator:Token, right:Expr"
        )
    )
}