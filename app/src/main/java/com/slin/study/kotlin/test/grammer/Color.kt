package com.slin.study.kotlin.test.grammer

import com.slin.study.kotlin.test.grammer.Color.*

enum class Color(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0),
    ORANGE(255, 165, 0),
    YELLOW(255, 255, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    INDIGO(75, 0, 130),
    VIOLTE(238, 130, 238);

    fun rgb() = (r * 256 + g) * 256 + b

}

fun main() {
    println(BLUE.rgb())
    println(getMnemonic(VIOLTE))
    println(getWarmth(RED))
    println(mix(RED, YELLOW))
    println(recognize('a'))
}

fun getMnemonic(color: Color) =
    when (color) {
        RED -> "Richard"
        ORANGE -> "Of"
        YELLOW -> "York"
        GREEN -> "Gave"
        BLUE -> "Battle"
        INDIGO -> "In"
        VIOLTE -> "Vain"
    }

fun getWarmth(color: Color) =
    when (color) {
        RED, ORANGE, YELLOW -> "warm"
        GREEN -> "neutral"
        BLUE, INDIGO, VIOLTE -> "cold"
    }

fun mix(c1: Color, c2: Color) =
    when (setOf(c1, c2)) {
        setOf(RED, YELLOW) -> ORANGE
        setOf(YELLOW, BLUE) -> GREEN
        setOf(BLUE, VIOLTE) -> INDIGO
        else -> throw Exception("dirty color")
    }

fun recognize(c: Char) =
    when (c) {
        in 'a'..'z' -> "It's a digit!"
        in '0'..'9' -> "It's a letter!"
        else -> "I don't know.."
    }