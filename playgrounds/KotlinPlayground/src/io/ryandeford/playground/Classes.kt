package io.ryandeford.playground

import java.util.*

class Classes {

    class Person(val name: String = names.random(), var mood: String = moods.random()) {
        fun getInfo() = "My name is '${name}' and my mood is '${mood}'"
    }

    data class PersonData(val id: String, val name: String, val mood: String)

    companion object {
        val names = listOf("Alex", "Bobby", "Chuck", "David", "Edgar", "Frank", "Greg", "Hank", "Ian", "Jack")
        val moods = listOf("Angry", "Calm", "Excited", "Fun", "Happy", "Hateful", "Lazy", "Sad", "Scared", "Tired")

        fun createPerson() = createPerson(name = names.random(), mood = moods.random())
        fun createPerson(name: String, mood: String) = Person(name = name, mood = mood)

        fun createPersonData() = createPersonData(id = UUID.randomUUID().toString(), name = names.random(), mood = moods.random())
        fun createPersonData(id: String, name: String, mood: String) = PersonData(id = id, name = name, mood = mood)
    }
}