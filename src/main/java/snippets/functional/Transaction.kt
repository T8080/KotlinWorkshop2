package snippets.functional

fun transaction(executable: () -> Unit) {
    println("open database")
    executable()
    println("close database")
}

fun main() = transaction {
    println("database operation")
}