package org.geepawhill.gph3

import java.net.InetSocketAddress
import java.net.ServerSocket

class ListenerTest(val acceptor: Acceptor, test: ListenerTest.() -> Unit) {
    val inProgress = TestInProgress()
    val socket = ServerSocket()
    val port: Int
    val listener: Listener

    init {
        socket.reuseAddress = true
        socket.bind(InetSocketAddress(0))
        port = socket.localPort
        listener = SimpleListener(acceptor, socket)

        val thread = Thread {
            listener.listenWhile(inProgress, 10)
        }
        thread.start()
        test()
        inProgress.finish()
        thread.join()
    }

    companion object {
        fun simple(acceptor: Acceptor, test: ListenerTest.() -> Unit) {
            ListenerTest(acceptor, test)
        }
    }
}