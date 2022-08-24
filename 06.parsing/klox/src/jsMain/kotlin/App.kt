import csstype.AlignItems
import csstype.Color
import csstype.Display
import csstype.FlexDirection
import emotion.react.css
import klox.Expr
import klox.Lox
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.useState
import ui.NavHeader

val App = FC<Props> {

    var kloxCode by useState("")
    var kloxExpr: Expr? by useState(null)

    fun onCodeChanged(code: String) {
        Lox.clearErrors()
        kloxCode = code
        kloxExpr = Lox.parse(code)

        if (Lox.error) {
            console.log(Lox.errors.joinToString("\n"))
        }
    }

    div {

        css {
            backgroundColor = Color("#fefefe")
        }

        NavHeader {
            title = "klox playground"
        }

        div {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
            }
            CodeArea {
                code = kloxCode
                onChanged = ::onCodeChanged
            }

            ASTArea {
                expr = kloxExpr
            }
        }

        div {
            ConsoleArea {
                messages = Lox.errors
            }
        }
    }

}