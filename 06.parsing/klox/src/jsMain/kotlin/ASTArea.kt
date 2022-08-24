import csstype.pct
import emotion.react.css
import klox.Expr
import klox.Lox
import klox.printers.TreantJsPrinter
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useState
import ui.Panel

external interface ASTAreaProps : Props {
    var expr: Expr?
}

val ASTArea = FC<ASTAreaProps> { props ->

    div {
        css {
            width = 100.pct
        }

        Panel {
            title = "AST"

            div {
                id = "chart"
            }

            val expr = props.expr

            if (!Lox.error && expr != null && js("window.Raphael")) {
                val ast = Lox.print(expr, TreantJsPrinter())
                val conf =
                    "{ \"chart\": { \"container\":\"#chart\" }, \"nodeStructure\": $ast }"
                Treant(JSON.parse(conf))
            }
        }
    }
}