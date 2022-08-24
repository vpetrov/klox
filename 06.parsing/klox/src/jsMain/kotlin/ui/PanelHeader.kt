package ui

import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.h2

external interface PanelHeaderProps : Props {
    var title: String
}

val PanelHeader = FC<PanelHeaderProps> { props ->
    h2 {
        css {
            fontSize = 10.px
            fontFamily = FontFamily.sansSerif
            textTransform = TextTransform.uppercase
            marginBlock = Margin(0.px, 0.px)
            padding = Padding(3.px, 5.px)
            backgroundColor = Color("#e3e3e3")
            color = Color("#333")
        }

        + props.title

    }


}