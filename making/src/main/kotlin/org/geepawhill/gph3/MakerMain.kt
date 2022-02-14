package org.geepawhill.gph3

import tornadofx.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket


class MakerMain : App(MakerView::class)

val html = """HTTP/1.1 200 OK
Content-Type: text/html

<html>Hi Mom!</html>
"""

class Server : Runnable {
    override fun run() {
        val socket = ServerSocket(8080)
        do {
            val accepted = socket.accept()
            val input = BufferedReader(InputStreamReader(accepted.getInputStream()))
            while (true) {
                val line = input.readLine();
                if (line.isEmpty()) break
                System.out.println(line);
            }
            val output = PrintWriter(accepted.getOutputStream())
            output.print(html)
            output.flush()
            output.close()
            accepted.close()
        } while (true)
    }

}

fun main(args: Array<String>) {
    val thread = Thread(Server())
    thread.isDaemon = true
    thread.start()
    launch<MakerMain>(args)
}
