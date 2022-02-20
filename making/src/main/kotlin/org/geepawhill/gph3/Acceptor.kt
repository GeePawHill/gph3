package org.geepawhill.gph3

import java.net.Socket

interface Acceptor {
    fun accept(socket: Socket)
}