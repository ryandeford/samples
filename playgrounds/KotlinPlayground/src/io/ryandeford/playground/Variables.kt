package io.ryandeford.playground

class Variables {

    fun executeAssignmentOperations() {
        val a = 1
        val b = 2.2222f
        val c = 3333.3333

        val x = 'x'
        val y = "y"
        val z = "z = ${(x + y)} with $x and $y"

        var nullableMutableType: Double? = null
        nullableMutableType = c + 2222.2222
    }

    fun getType(item: Any?): String {
        val type = when (item) {
            null -> "Null"
            else -> item::class.simpleName
        }

        return when (type) {
            null -> "NullType"
            else -> type
        }
    }
}