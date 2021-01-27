package snippets.oop

data class Vector(
    val x: Int,
    val y: Int
) {
    operator fun plus(other: Vector) = Vector(this.x + other.x, this.y + other.y)

    operator fun minus(other: Vector) = Vector(this.x - other.x, this.y - other.y)
}

fun main() {
    val a = Vector(1, 2)
    val b = Vector(3, 4)

    val c = a + b
    val d = c - a

    println(c)

    val (x, y) = c
    println(x + y)
}