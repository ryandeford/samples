package io.ryandeford.playground

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class VariablesTest {

    @Test
    fun executeAssignmentOperations() {
        Variables().executeAssignmentOperations()
    }

    @Test
    fun getType() {
        val variable = Variables()
        assertEquals("Int", variable.getType(item = 1))
        assertEquals("Float", variable.getType(item = 1.0f))
        assertEquals("Double", variable.getType(item = 1.0))
        assertEquals("String", variable.getType(item = "test"))
        assertEquals("SingletonSet", variable.getType(item = setOf(0)))
        assertEquals("LinkedHashSet", variable.getType(item = mutableSetOf(0)))
        assertEquals("SingletonList", variable.getType(item = listOf(0)))
        assertEquals("ArrayList", variable.getType(item = mutableListOf(0)))
        assertEquals("SingletonMap", variable.getType(item = mapOf(0 to 0)))
        assertEquals("LinkedHashMap", variable.getType(item = mutableMapOf(0 to 0)))
        assertEquals("Null", variable.getType(item = null))
    }
}