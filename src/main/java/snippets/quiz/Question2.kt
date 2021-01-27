package snippets.quiz

fun main() {
    println(function(listOf(1, 2, 3), listOf(2, 3, 4)))
}

fun <T> function(a: List<T>, b: List<T>): List<T> {
    val c = mutableListOf<T>()

    for (x in a) {
        if (x in b && x !in c) {
            c += x
        }
    }

    return c
}

fun main1() {
    println(listOf(1, 2, 3) intersect listOf(2, 3, 4))
}