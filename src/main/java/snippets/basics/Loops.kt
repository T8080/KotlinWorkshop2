package snippets.basics

fun main() {
    for (i in 0..10) {
        println("i: $i")
    }

    for (i in 0 until 10) {
        println("i: $i")
    }

    for (i in listOf(10, 5, 0)) {
        println("i: $i")
    }
}