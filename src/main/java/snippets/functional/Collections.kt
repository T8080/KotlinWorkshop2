package snippets.functional

fun main() {
    val list = (0..100).toList()

    val x = list
        .filter { it % 2 == 0 }
        .map { it.toString() }
        .distinctBy { it.length }
        .sorted()

    println(x)
}