package io.ryandeford.playground

class Collections {

    fun createImmutableSet(vararg input: Any?): Set<Any?> {
        return setOf(*input)
    }

    fun createMutableSet(vararg input: Any?): MutableSet<Any?> {
        return mutableSetOf(*input)
    }

    fun createImmutableList(vararg input: Any?): List<Any?> {
        return listOf(*input)
    }

    fun createMutableList(vararg input: Any?): MutableList<Any?> {
        return mutableListOf(*input)
    }

    fun createImmutableMap(vararg input: Pair<Any?, Any?>): Map<Any?, Any?> {
        return mapOf(*input)
    }

    fun createMutableMap(vararg input: Pair<Any?, Any?>): MutableMap<Any?, Any?> {
        return mutableMapOf(*input)
    }
}