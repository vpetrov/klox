package printers

import org.junit.jupiter.api.Assertions.assertEquals

import Token
import org.junit.jupiter.api.Test

class AstPrinterTest {

    @Test
    // (* (- 123) (group 45.67))
    fun `test a simple Expr`() {
        val fortyFive = Expr.Literal(45.67)
        val grouping = Expr.Grouping(fortyFive)
        val star = Token(TokenType.STAR, "*",null, 1)
        val oneHundredTwentyThree = Expr.Literal(123)
        val minus = Token(TokenType.MINUS, "-", null, 1)
        val unary = Expr.Unary(minus, oneHundredTwentyThree)

        val expression = Expr.Binary(unary, star, grouping)

        // run the AstPrinter
        val result = AstPrinter().print(expression)

        assertEquals("(* (- 123) (group 45.67))", result)
    }
}