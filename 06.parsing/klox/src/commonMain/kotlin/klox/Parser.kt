package klox

import klox.TokenType.*

class Parser(val initialTokens:List<Token> = emptyList()) {
    private val _tokens = mutableListOf<Token>()
    val tokens = _tokens as List<Token>

    private var current = 0

    init {
        _tokens.addAll(initialTokens)
    }

    fun parse():Expr? {
        try {
            return expression()
        } catch (error:ParseError) {
            return null
        }
    }

    private fun expression():Expr = equality()

    private fun equality():Expr {
        var expr = comparison()

        while (matchTokens(BANG_EQUAL, EQUAL_EQUAL)) {
            val operator = previous()
            val right = comparison()
            expr = Expr.Binary(expr, operator, right)
        }

        return expr
    }

    private fun comparison():Expr {
        var expr = term()

        while (matchTokens(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            val operator = previous()
            val right = term()
            expr = Expr.Binary(expr, operator, right)
        }

        return expr
    }

    private fun term():Expr {
        var expr = factor()

        while (matchTokens(MINUS, PLUS)) {
            val operator = previous()
            val right = factor()
            expr = Expr.Binary(expr, operator, right)
        }

        return expr
    }

    private fun factor():Expr {
        var expr = unary()

        while (matchTokens(SLASH, STAR)) {
            val operator = previous()
            val right = unary()
            expr = Expr.Binary(expr, operator, right)
        }

        return expr
    }

    private fun unary():Expr {
        if (matchTokens(BANG, MINUS)) {
            val operator = previous()
            val right = unary()
            return Expr.Unary(operator, right)
        }

        return primary()
    }

    private fun primary():Expr {
        return if (matchTokens(FALSE)) Expr.Literal(false)
        else if (matchTokens(TRUE)) Expr.Literal(true)
        else if (matchTokens(NIL)) Expr.Literal("nil")
        else if (matchTokens(NUMBER, STRING)) Expr.Literal(previous().literal!!)
        else if (matchTokens(LEFT_PAREN)) {
            val expr = expression()
            consume(RIGHT_PAREN, "Expect ')' after expression.")
            Expr.Grouping(expr)
        } else throw kloxError(peek(),"Expect expression.")
    }

    private fun consume(type:TokenType, message:String):Token {
        if (check(type)) {
            return advance()
        }

        throw kloxError(peek(), message)
    }

    private fun synchronize() {
        advance()

        while (!isAtEnd()) {
            if (previous().type == SEMICOLON) return

            when (peek().type) {
                CLASS, FUN, VAR, FOR, IF, WHILE, PRINT, RETURN -> return
                else -> advance()
            }
        }
    }

    private fun kloxError(token:Token, message: String):ParseError {
        Lox.error(token, message)
        return ParseError()
    }

    private fun matchTokens(vararg types:TokenType):Boolean {
        types.forEach { type ->
            if (check(type)) {
                advance()
                return true
            }
        }

        return false
    }

    private fun check(type:TokenType):Boolean {
        if (isAtEnd()) return false;

        return peek().type == type
    }

    private fun advance():Token {
        if (!isAtEnd()) {
            current++
        }

        return previous()
    }

    private fun isAtEnd():Boolean = peek().type == TokenType.EOF

    private fun peek():Token = _tokens[current]

    private fun previous():Token = tokens[current - 1]

    private class ParseError: Error() {}
}