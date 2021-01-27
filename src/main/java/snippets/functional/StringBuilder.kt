package snippets.functional

fun buildString(block: StringBuilder.() -> Unit): String {
    val builder = StringBuilder()
    builder.block()
    return builder.toString()
}

fun main() {
    val list = listOf(1, 2, 3)

    val builder = StringBuilder()
    builder.append("[")
    for (item in list) {
        builder.append(item)
        if (item != list.last()) builder.append(", ")
    }
    builder.append("]")

    val str = builder.toString()

    println(str)
}

fun main2() {
    val list = listOf(1, 2, 3)

    val str = buildString {
        append("[")
        for (item in list) {
            append(item)
            if (item != list.last()) append(", ")
        }
        append("]")
    }

    println(str)
}