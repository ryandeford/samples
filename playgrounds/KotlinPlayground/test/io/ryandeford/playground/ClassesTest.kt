package io.ryandeford.playground

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class ClassesTest {

    @Test
    fun createPersonDefault() {
        val person = Classes.createPerson()
        assertNotNull(person)
        assertNotNull(person.name)
        assertNotNull(person.mood)
        assertTrue(person.name in Classes.names)
        assertTrue(person.mood in Classes.moods)

        val info = person.getInfo()
        assertNotNull(info)
        assertEquals("My name is '${person.name}' and my mood is '${person.mood}'", info)
    }

    @Test
    fun createPersonCustom() {
        val customName = "Custom Name"
        val customMood = "Custom Mood"

        val person = Classes.createPerson(name = customName, mood = customMood)
        assertNotNull(person)
        assertEquals(customName, person.name)
        assertEquals(customMood, person.mood)

        val info = person.getInfo()
        assertNotNull(info)
        assertEquals("My name is '${customName}' and my mood is '${customMood}'", info)
    }

    @Test
    fun createPersonDataDefault() {
        val personData = Classes.createPersonData()
        assertNotNull(personData)
        assertNotNull(personData.id)
        assertNotNull(personData.name)
        assertNotNull(personData.mood)
        assertDoesNotThrow { UUID.fromString(personData.id) }
        assertTrue(personData.name in Classes.names)
        assertTrue(personData.mood in Classes.moods)
    }

    @Test
    fun createPersonDataCustom() {
        val customId = "Custom ID"
        val customName = "Custom Name"
        val customMood = "Custom Mood"

        val personData = Classes.createPersonData(id = customId, name = customName, mood = customMood)
        assertNotNull(personData)
        assertEquals(customId, personData.id)
        assertEquals(customName, personData.name)
        assertEquals(customMood, personData.mood)
    }
}