package snippets.basics

fun main() {
    greet("hi", name = "Audience")
}

fun greet(greeting: String = "Hello", name: String = "World") {
    println("$greeting, $name!")
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun sum2(a: Int, b: Int): Int = a + b