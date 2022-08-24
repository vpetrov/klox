import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.textarea
import ui.Panel

external interface CodeAreaProps : Props {
    var code: String
    var onChanged: (String) -> Unit
}

val CodeArea = FC<CodeAreaProps> { props ->
    div {
        css {
            width = 100.pct
        }

        Panel {
            title = "Code"

            textarea {
                cols = 80
                rows = 24
                placeholder = "type code here"

                css {
                    width = 100.pct
                    boxSizing = BoxSizing.borderBox
                    display = Display.block
                    border = Border(0.px, LineStyle.hidden)
                    backgroundColor = NamedColor.transparent
                }
                +props.code
                onChange = { event ->
                    console.log("code changed!", event.target.value)
                    props.onChanged(event.target.value)
                }
            }
        }
    }
}