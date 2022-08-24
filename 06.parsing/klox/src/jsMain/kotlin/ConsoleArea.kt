import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import ui.Panel

external interface ConsoleAreaProps : Props {
    var messages:List<String>
}

val ConsoleArea = FC<ConsoleAreaProps>{ props ->

    Panel {
        title = "Messages"

        props.messages.forEach { message ->
            div {
                +message
            }
        }
    }
}