package printers

import Expr
import java.lang.StringBuilder

class TreantJsPrinter: Expr.Visitor<String> {
    override fun visitBinaryExpr(expr: Expr.Binary) = genJsonNode(expr.operator.lexeme, expr.left, expr.right)

    override fun visitGroupingExpr(expr: Expr.Grouping) = genJsonNode("group", expr.expression)

    override fun visitLiteralExpr(expr: Expr.Literal) = genJsonNode(expr.value.toString())

    override fun visitUnaryExpr(expr: Expr.Unary) = genJsonNode(expr.operator.lexeme, expr.right)

    fun toJson(expr:Expr):String = expr.accept(this)

    fun genJsonNode(name:String, vararg exprs: Expr):String {
        val sb = StringBuilder("{ text: { name: '$name' }, children: [ ")
        exprs.forEach { expr ->
            sb.append(expr.accept(this))
            if (exprs.size > 1) {
                sb.append(", ")
            }
        }

        sb.append(" ] }")

        return sb.toString()
    }
}