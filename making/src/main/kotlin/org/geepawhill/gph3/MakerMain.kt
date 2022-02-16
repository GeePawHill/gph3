package org.geepawhill.gph3

import tornadofx.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket


class MakerMain : App(MakerView::class)

val html = """HTTP/1.1 200 OK
Content-Type: text/html

<html>Hi Mom!</html>
"""

interface Acceptor {
    fun accept(socket: Socket)
}

class HttpAcceptor : Acceptor {
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

class Server(val acceptor: Acceptor) : Runnable {
    override fun run() {
        val socket = ServerSocket(8080)
        do {
            acceptor.accept(socket.accept())
        } while (true)
    }
}

fun main(args: Array<String>) {
    val acceptor = HttpAcceptor()
    val thread = Thread(Server(acceptor))
    thread.isDaemon = true
    thread.start()
    launch<MakerMain>(args)
}
