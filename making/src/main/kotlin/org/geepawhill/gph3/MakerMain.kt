package org.geepawhill.gph3

import tornadofx.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketTimeoutException


class MakerMain : App(MakerView::class)

val html = """HTTP/1.1 200 OK
Content-Type: text/html

<html>Hi Mom!</html>
"""

interface Acceptor {
    fun accept(socket: Socket)
}

class SimpleAcceptor : Acceptor {
    override fun accept(socket: Socket) {
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        while (true) {
            val line = input.readLine();
            if (line.isEmpty()) break
            System.out.println(line);
        }
        val output = PrintWriter(socket.getOutputStream())
        output.print(html)
        output.flush()
        output.close()
        socket.close()
    }
}

interface ContinueFlag {
    val isTrue: Boolean

    companion object {
        val FOREVER = object : ContinueFlag {
            override val isTrue: Boolean = true
        }
    }
}

interface Listener {
    fun listenWhile(continueFlag: ContinueFlag)
}

class SimpleListener(val acceptor: Acceptor) : Listener {
    override fun listenWhile(continueFlag: ContinueFlag) {
        val socket = ServerSocket(8080)
        while (continueFlag.isTrue) {
            socket.soTimeout = 100
            try {
                val accepted = socket.accept()
                acceptor.accept(accepted)
            } catch (ignored: SocketTimeoutException) {
            }
        }
        socket.close()
    }
}

fun main(args: Array<String>) {
    val thread = Thread {
        val listener = SimpleListener(SimpleAcceptor())
        listener.listenWhile(ContinueFlag.FOREVER)
    }
    thread.isDaemon = true
    thread.start()
    launch<MakerMain>(args)
}
