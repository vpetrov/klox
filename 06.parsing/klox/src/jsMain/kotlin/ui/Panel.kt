package ui

import csstype.Border
import csstype.Color
import csstype.LineStyle
import csstype.px
import emotion.react.css
import react.FC
import react.Props
import react.PropsWithChildren
import react.dom.html.ReactHTML.div

external interface PanelProps : PropsWithChildren {
    var title: String
}

val Panel = FC <PanelProps> { props ->

    div {

        css {
            margin = 2.5.px
            border = Border(1.px, LineStyle.solid, Color("#e3e3e3"))
        }

        PanelHeader {
            title = props.title
        }

        PanelBody {
            children = props.children
        }
    }
}