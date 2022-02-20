package org.geepawhill.gph3

import java.util.concurrent.atomic.AtomicBoolean

class TestInProgress : ContinueFlag {
    val keepGoing = AtomicBoolean(true)

    override val isTrue: Boolean
        get() = keepGoing.get()

    fun finish() {
        keepGoing.set(false)
    }
}