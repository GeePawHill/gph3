package org.geepawhill.gph3

import org.geepawhill.gph3.ListenerTest.Companion.simple
import org.junit.jupiter.api.Test
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SimpleListenerTest {

    @Test
    fun `responds`() {
        simple(SimpleAcceptor()) {
            val connection = URL("http://localhost:$port").openConnection() as HttpURLConnection
            connection.connect()
            val reader = InputStreamReader(connection.inputStream)
            reader.readLines().forEach {
                println(it)
            }
            connection.disconnect()
        }
    }

    @Test
    fun `responds 2`() {
        simple(SimpleAcceptor()) {
            val connection = URL("http://localhost:$port").openConnection() as HttpURLConnection
            connection.connect()
            val reader = InputStreamReader(connection.inputStream)
            reader.readLines().forEach {
                println(it)
            }
            connection.disconnect()
        }
    }
}