package org.geepawhill.gph3

import javafx.scene.web.WebView
import tornadofx.*

class MakerView : View("GPH3 (Making)") {

    val browser = WebView()

    override val root = borderpane {
        top = toolbar {
            button("local") {
                action {
                    browser.engine.load("http://localhost:8080")
                }
            }
            button("gph") {
                action {
                    browser.engine.load("http://geepawhill.org")
                }
            }
        }
        center = browser
    }
}