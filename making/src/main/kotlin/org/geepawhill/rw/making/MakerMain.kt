package org.geepawhill.rwm

import io.undertow.Undertow
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers
import tornadofx.*


class MakerMain : App(MakerView::class)

fun main(args:Array<String>) {
    val server = Undertow.builder()
        .addHttpListener(8080, "localhost")
        .setHandler { exchange ->
            exchange.responseHeaders.put(Headers.CONTENT_TYPE, "text/plain")
            exchange.responseSender.send("Hello World")
        }.build()
    server.start()
    launch<MakerMain>(args)
}