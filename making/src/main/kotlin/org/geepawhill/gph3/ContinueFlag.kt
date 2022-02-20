package org.geepawhill.gph3

interface ContinueFlag {
    val isTrue: Boolean

    companion object {
        val FOREVER = object : ContinueFlag {
            override val isTrue: Boolean = true
        }
    }
}