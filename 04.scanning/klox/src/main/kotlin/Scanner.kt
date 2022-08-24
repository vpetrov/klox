import TokenType.*

private val numbers = '0'..'9'
private val alpha = ('A'..'Z') + ('a'..'z') + '_'
private val alphanumeric = numbers + alpha

private val keywords = mapOf(
    "and" to AND,
    "class" to CLASS,
    "else" to ELSE,
    "false" to FALSE,
    "for" to FOR,
    "fun" to FUN,
    "if" to IF,
    "nil" to NIL,
    "or" to OR,
    "print" to PRINT,
    "return" to RETURN,
    "super" to SUPER,
    "this" to THIS,
    "true" to TRUE,
    "var" to VAR,
    "while" to WHILE
)

class Scanner(val source: String) {

    private val tokens = mutableListOf<Token>()

    private var start = 0
    private var current = 0
    private var line = 1

    fun scanTokens(): List<Token> {
        while (!isAtEnd()) {
            start = current
            scanToken()
        }

        tokens.add(Token(EOF, "", "", line))
        return tokens
    }

    private fun scanToken() {
        val c = advance()
        when (c) {
            '(' -> addToken(LEFT_PAREN)
            ')' -> addToken(RIGHT_PAREN)
            '{' -> addToken(LEFT_BRACE)
            '}' -> addToken(RIGHT_BRACE)
            ',' -> addToken(COMMA)
            '.' -> addToken(DOT)
            '-' -> addToken(MINUS)
            '+' -> addToken(PLUS)
            ';' -> addToken(SEMICOLON)
            '*' -> addToken(STAR)
            '!' -> addToken(if (match('=')) BANG_EQUAL else BANG)
            '=' -> addToken(if (match('=')) EQUAL_EQUAL else EQUAL)
            '<' -> addToken(if (match('=')) LESS_EQUAL else LESS)
            '>' -> addToken(if (match('=')) GREATER_EQUAL else GREATER)
            '/' -> if (match('/')) {
                // A comment goes until the end of the line
                while (peek() != '\n' && !isAtEnd()) {
                    advance()
                }
            } else {
                addToken(SLASH)
            }
            ' ', '\r', '\t' -> {} // ignore
            '\n' -> line += 1
            '"' -> string()
            in numbers -> number()
            in alpha -> identifier()
            else -> Lox.error(line, "Unexpected character: '$c'")
        }
    }

    private fun identifier() {
        while (peek() in alphanumeric) {
            advance()
        }

        val text = source.substring(start, current)
        val type = keywords.getOrDefault(text, IDENTIFIER)
        addToken(type)
    }

    private fun number() {
        while (isDigit(peek())) {
            advance()
        }

        // look for a fractional part
        if (peek() == '.' && isDigit(peekNext())) {
            advance()

            while (isDigit(peek())) {
                advance()
            }
        }

        addToken(NUMBER, source.substring(start, current).toDouble())
    }

    private fun isDigit(c: Char) = c in numbers

    private fun string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') {
                line += 1
            }
            advance()
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated string")
            return
        }

        advance()

        val value = source.substring(start + 1, current - 1)
        addToken(STRING, value)
    }

    private fun peek(): Char = if (isAtEnd()) Char.MIN_VALUE else source[current]

    private fun peekNext() = if (current + 1 >= source.length) Char.MIN_VALUE else source[current + 1]

    private fun match(expected: Char): Boolean {
        if (isAtEnd() || source[current] != expected) {
            return false
        }

        current += 1
        return true
    }

    private fun advance(): Char = source[current].also { current += 1 }

    private fun addToken(type: TokenType, literal: Any? = null) {
        val text = source.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }

    private fun isAtEnd() = current >= source.length
}