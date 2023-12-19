package io.ryandeford.playground

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CollectionsTest {

    @Test
    fun createImmutableSet() {
        val createdSet = Collections().createImmutableSet(1,2,3)
        assertEquals(setOf(1,2,3), createdSet)
        assertEquals(3, createdSet.size)
    }

    @Test
    fun createMutableSet() {
        val createdSet = Collections().createMutableSet(1,2,3)
        assertEquals(setOf(1,2,3), createdSet)
        assertEquals(3, createdSet.size)

        createdSet.add(4)
        assertEquals(setOf(1,2,3,4), createdSet)
        assertEquals(4, createdSet.size)

        createdSet.remove(1)
        assertEquals(setOf(2,3,4), createdSet)
        assertEquals(3, createdSet.size)
    }

    @Test
    fun createImmutableList() {
        val createdList = Collections().createImmutableList(1,2,3)
        assertEquals(listOf(1,2,3), createdList)
        assertEquals(3, createdList.size)
    }

    @Test
    fun createMutableList() {
        val createdList = Collections().createMutableList(1,2,3)
        assertEquals(listOf(1,2,3), createdList)
        assertEquals(3, createdList.size)

        createdList.add(4)
        assertEquals(listOf(1,2,3,4), createdList)
        assertEquals(4, createdList.size)

        createdList.remove(1)
        assertEquals(listOf(2,3,4), createdList)
        assertEquals(3, createdList.size)
    }

    @Test
    fun createImmutableMap() {
        val createdMap = Collections().createImmutableMap("1" to 1, "2" to 2, "3" to 3)
        assertEquals(mapOf("1" to 1, "2" to 2, "3" to 3), createdMap)
        assertEquals(3, createdMap.size)
    }

    @Test
    fun createMutableMap() {
        val createdMap = Collections().createMutableMap("1" to 1, "2" to  2, "3" to 3)
        assertEquals(mapOf("1" to 1, "2" to 2, "3" to 3), createdMap)
        assertEquals(3, createdMap.size)

        createdMap["4"] = 4
        assertEquals(mapOf("1" to 1, "2" to 2, "3" to 3, "4" to 4), createdMap)
        assertEquals(4, createdMap.size)

        createdMap.remove("1")
        assertEquals(mapOf("2" to 2, "3" to 3, "4" to 4), createdMap)
        assertEquals(3, createdMap.size)
    }
}