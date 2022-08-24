package printers

import Expr
import java.lang.StringBuilder

class AstPrinter : Expr.Visitor<String> {

    fun print(expr:Expr) = expr.accept(this)

    override fun visitBinaryExpr(expr: Expr.Binary) = parenthesize(expr.operator.lexeme, expr.left, expr.right)

    override fun visitGroupingExpr(expr: Expr.Grouping) = parenthesize("group", expr.expression)

    override fun visitLiteralExpr(expr: Expr.Literal) = expr.value.toString()

    override fun visitUnaryExpr(expr: Expr.Unary) = parenthesize(expr.operator.lexeme, expr.right)

    private fun parenthesize(name:String, vararg exprs:Expr):String {
        val builder = StringBuilder("($name")

        exprs.forEach { expr -> builder.append(" ").append(expr.accept(this)) }

        return builder.append(")").toString()
    }
}