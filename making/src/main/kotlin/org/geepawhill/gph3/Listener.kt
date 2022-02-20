package org.geepawhill.gph3

interface Listener {
    fun listenWhile(continueFlag: ContinueFlag, timeout: Int = 1000)
}