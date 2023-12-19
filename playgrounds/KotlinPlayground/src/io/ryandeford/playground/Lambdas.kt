package io.ryandeford.playground

class Lambdas {

    fun executeLambda(lambda: () -> Any?): Any? {
        return lambda()
    }

    fun executeLambdaWithParameters(lambda: (a: Int, b: Int) -> Int?, a: Int, b: Int): Int? {
        return lambda(a, b)
    }

    fun <T> executeLambdaWithGenericParameters(lambda: (input: List<T>) -> List<T>, input: List<T>): List<T> {
        return lambda(input)
    }

    fun <T> executeLambdaWithVariableParameters(lambda: (input: Array<out T>) -> String, vararg input: T): String {
        return lambda(input)
    }

    fun test(vararg strings: String) {

    }
}