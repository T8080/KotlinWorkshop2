package snippets.functional

fun <T> filterList(list: List<T>, predicate: (T) -> Boolean): List<T> {
    val result = mutableListOf<T>()

    for (item in list) {
        if (predicate(item)) {
            result += item
        }
    }

    return result
}

fun <T> List<T>.filter(predicate: (T) -> Boolean): List<T> {
    return filterList(this, predicate)
}

fun main() {
    val numbers = (1..10).toList()

    val result = numbers.filter { it > 5 }

    println(result)
}