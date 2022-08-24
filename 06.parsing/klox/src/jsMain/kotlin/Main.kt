import kotlinx.browser.document
import kotlinext.js.require
import react.create
import react.dom.client.createRoot

fun main() {
    require("./treant.css")
    require("./treant.min.js")

    val container = document.createElement("div")
    document.body!!.appendChild(container)

    val script = document.createElement("script")
    script.setAttribute("src", "https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.4/raphael-min.js")
    document.head!!.appendChild(script)

    createRoot(container).render(App.create())
}