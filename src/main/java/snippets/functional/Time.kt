package snippets.functional

fun time(executable: () -> Unit): Long {
    val start = System.currentTimeMillis()
    executable()
    val end = System.currentTimeMillis()

    return end - start
}

fun main() {
    val t = time {
        Thread.sleep(1000L)
    }

    println("operation took $t milliseconds")
}