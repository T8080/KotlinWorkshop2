package snippets.functional

fun repeat(times: Int, block: (Int) -> Unit) {
    for (i in 0 until times) {
        block(i)
    }
}

fun main() {
    repeat(10) {
        println("Hello World")
    }
}