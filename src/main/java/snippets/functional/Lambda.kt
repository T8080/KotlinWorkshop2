package snippets.functional

fun main() {
    val l0: () -> Unit           = {}

    val l1: () -> Int            = { 10 }

    val l2: (Int) -> Int         = { x -> x * 2}

    val l3: (Int) -> Int         = { it * 2 }

    val l4: (Int, Int) -> Int    = { a, b -> a + b }

    val l5: String.() -> Int     = { this.length }

    val l6: String.(Int) -> Char = { this[it] }
}