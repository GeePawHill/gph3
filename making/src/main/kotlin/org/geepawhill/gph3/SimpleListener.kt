package org.geepawhill.gph3

import java.net.ServerSocket
import java.net.SocketTimeoutException

class SimpleListener(val acceptor: Acceptor, val socket: ServerSocket) : Listener {

    constructor(acceptor: Acceptor, port: Int) : this(acceptor, ServerSocket(port))

    override fun listenWhile(continueFlag: ContinueFlag, timeout: Int) {
        while (continueFlag.isTrue) {
            socket.soTimeout = timeout
            try {
                val accepted = socket.accept()
                acceptor.accept(accepted)
            } catch (ignored: SocketTimeoutException) {
            }
        }
        socket.close()
    }
}