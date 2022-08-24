package ui

import csstype.Color
import csstype.Display
import csstype.Padding
import csstype.px
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span

external interface NavHeaderProps : Props {
    var title: String
}

val NavHeader = FC<NavHeaderProps> { props ->
    div {
        css {
            backgroundColor = Color("#666")
            padding = Padding(10.px, 10.px)
            marginBottom = 2.5.px
        }

        span {
            css {
                display = Display.inlineBlock
                color = Color("#f3f3f3")
                fontSize = 16.px
            }

            +props.title
        }
    }
}