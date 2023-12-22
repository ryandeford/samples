package io.ryandeford.playground

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExtensionsTests {

    @Test
    fun addStaticExtensionMethod() {
        fun String.Companion.customStaticExtension(s: String): String {
            return "custom static extension for: ${s}"
        }

        assertEquals("custom static extension for: test", String.customStaticExtension("test"))
    }

    @Test
    fun addInstanceExtensionMethod() {
        fun Number.toFriendlyName(): String {
            return when(this) {
                1 -> "one"
                1.0 -> "one point oh"
                2 -> "two"
                2.0 -> "two point oh"
                3 -> "three"
                3.0 -> "three point oh"
                else -> "whoops: a friendly name hasn't been assigned yet"
            }
        }

        assertEquals("one", 1.toFriendlyName())
        assertEquals("two", 2.toFriendlyName())
        assertEquals("three", 3.toFriendlyName())
        assertEquals("one point oh", 1.0.toFriendlyName())
        assertEquals("two point oh", 2.0.toFriendlyName())
        assertEquals("three point oh", 3.0.toFriendlyName())
        assertEquals("whoops: a friendly name hasn't been assigned yet", 100.toFriendlyName())
    }
}