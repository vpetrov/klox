package ui

import csstype.Color
import csstype.NamedColor
import csstype.px
import emotion.react.css
import react.FC
import react.PropsWithChildren
import react.dom.html.ReactHTML.div

external interface PanelBodyProps : PropsWithChildren

val PanelBody = FC<PanelBodyProps> { props ->
    div {
        id = "panel_body"

        css {
            padding = 5.px
            backgroundColor = Color("#fafafa")
        }

        +props.children
    }
}