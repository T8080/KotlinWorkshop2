package snippets.basics

import kotlin.random.Random

fun main() {
    val random: Boolean = Random.nextBoolean()

    var x: Int = 0
    if (random) {
        x = 10
    } else {
        x = 15
    }

    var y: Int = if (random) {
        10
    } else {
        15
    }

    var z: Int = if (random) 10 else 15

    var str: String = when(Random.nextInt(3)) {
        0 -> "zero"
        1 -> "one"
        2 -> "three"
        else -> "unknown"
    }
}