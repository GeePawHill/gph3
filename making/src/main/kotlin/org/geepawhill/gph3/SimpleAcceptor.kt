package org.geepawhill.gph3

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class SimpleAcceptor : Acceptor {
    val html = """HTTP/1.1 200 OK
Content-Type: text/html

<html>Hi Mom!</html>
"""

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