package org.geepawhill.gph3

import org.junit.jupiter.api.Test
import java.io.InputStreamReader
import java.net.HttpURLConnection
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
        val listener = SimpleListener(SimpleAcceptor())
        val thread = Thread {
            listener.listenWhile(inProgress)
        }
        thread.start()
        // test goes here
        val connection = URL("http://localhost:8080").openConnection() as HttpURLConnection
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

    @Test
    fun `responds 2`() {
        val listener = SimpleListener(SimpleAcceptor())
        val thread = Thread {
            listener.listenWhile(inProgress)
        }
        thread.start()
        // test goes here
        val connection = URL("http://localhost:8080").openConnection() as HttpURLConnection
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