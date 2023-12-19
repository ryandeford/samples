package io.ryandeford.playground

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LambdasTest {

    private val lambdas = Lambdas()

    @Test
    fun executeLambda() {
        val lambda = { 123 }

        val result = lambdas.executeLambda(lambda = lambda)

        assertEquals(123, result)
    }

    @Test
    fun executeLambdaWithParameters() {
        val lambdaAdd = { a: Int, b: Int -> a + b }
        val lambdaSubtract = { a: Int, b: Int -> a - b }
        val lambdaMultiply = { a: Int, b: Int -> a * b }
        val lambdaDivide = { a: Int, b: Int -> a / b }

        val resultAdd = lambdas.executeLambdaWithParameters(lambdaAdd, 222, 111)
        assertEquals(333, resultAdd)

        val resultSubtract = lambdas.executeLambdaWithParameters(lambdaSubtract, 222, 111)
        assertEquals(111, resultSubtract)

        val resultMultiply = lambdas.executeLambdaWithParameters(lambdaMultiply, 222, 111)
        assertEquals(24642, resultMultiply)

        val resultDivide = lambdas.executeLambdaWithParameters(lambdaDivide, 222, 111)
        assertEquals(2, resultDivide)
    }

    @Test
    fun executeLambdaWithGenericParameters() {
        val lambdaDoubleItems = { items: List<Int> ->
            val result = mutableListOf<Int>()
            for (item in items) {
                result.add(item * 2)
            }
            result.toList()
        }

        val resultDoubleItems = lambdas.executeLambdaWithGenericParameters(lambdaDoubleItems, listOf(1,2,3,4,5))
        assertEquals(listOf(2,4,6,8,10), resultDoubleItems)

        val lambdaReverseItems = { items: List<String> ->
            val result = mutableListOf<String>()
            for (item in items) {
                result.add(item.reversed())
            }
            result.toList()
        }

        val resultReverseItems = lambdas.executeLambdaWithGenericParameters(
            lambdaReverseItems,
            listOf("abc","123","test","reverseme","woohoo!")
        )
        assertEquals(
            listOf("cba","321","tset","emesrever","!oohoow"),
            resultReverseItems
        )
    }

    @Test
    fun executeLambdaWithVariableParameters() {
        val lambdaConcatItems = { items: Array<out Any> ->
            items.joinToString(",")
        }

        val resultConcatItems = lambdas.executeLambdaWithVariableParameters(
            lambdaConcatItems,
            "111", "222", "333", "444", "555"
        )
        assertEquals("111,222,333,444,555", resultConcatItems)

        val lambdaAddItems = { items: Array<out Number> ->
            var sum = 0.0
            for (item in items) {
                sum += item.toDouble()
            }
            sum.toString()
        }

        val resultAddItems = lambdas.executeLambdaWithVariableParameters(
            lambdaAddItems,
            1, 2.0, 3f, 4L, 0b00000101,
        )
        assertEquals("15.0", resultAddItems)
    }
}