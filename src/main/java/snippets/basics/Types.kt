package snippets.basics

import java.util.*

fun main() {
    val x: Int = 10
    val y: Double = 20.0
    val string: String = "Hello World!"
    val list: List<Int> = listOf(1, 2, 3)

    val scanner: Scanner = Scanner(System.`in`)

    val a: Int = scanner.nextInt()
    val b: Int = scanner.nextInt()
    println(a + b)
}