package org.geepawhill.gph3

import org.junit.jupiter.api.Test
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean

class SimpleListenerTest {

    class TestInProgress : ContinueFlag {
        val keepGoing = AtomicBoolean(true)

        override val isTrue: Boolean
            get() = keepGoing.get()

        fun finish() {
            keepGoing.set(false)
        }
    }

    val inProgress = TestInProgress()

    @Test
    fun `responds`() {
        val socket = ServerSocket()
        socket.reuseAddress = true
        socket.bind(InetSocketAddress(0))
        val port = socket.localPort

        val listener = SimpleListener(SimpleAcceptor(), socket)
        val thread = Thread {
            listener.listenWhile(inProgress, 10)
        }
        thread.start()
        // test goes here
        val connection = URL("http://localhost:$port").openConnection() as HttpURLConnection
        connection.connect()
        val reader = InputStreamReader(connection.inputStream)
        reader.readLines().forEach {
            println(it)
        }
        connection.disconnect()
        // test ends here

        inProgress.finish()
        thread.join()
    }

    @Test
    fun `responds 2`() {
        val socket = ServerSocket()
        socket.reuseAddress = true
        socket.bind(InetSocketAddress(0))
        val port = socket.localPort

        val listener = SimpleListener(SimpleAcceptor(), socket)
        val thread = Thread {
            listener.listenWhile(inProgress, 10)
        }
        thread.start()
        // test goes here
        val connection = URL("http://localhost:$port").openConnection() as HttpURLConnection
        connection.connect()
        val reader = InputStreamReader(connection.inputStream)
        reader.readLines().forEach {
            println(it)
        }
        // test ends here
        connection.disconnect()
        inProgress.finish()
        thread.join()
    }
}