package org.geepawhill.gph3

import tornadofx.*


class MakerMain : App(MakerView::class)

fun main(args: Array<String>) {
    val thread = Thread {
        val listener = SimpleListener(SimpleAcceptor(), 8080)
        listener.listenWhile(ContinueFlag.FOREVER, 1000)
    }
    thread.isDaemon = true
    thread.start()
    launch<MakerMain>(args)
}
