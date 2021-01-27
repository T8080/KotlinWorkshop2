package snippets.functional

fun <K, V> buildMap(builder: MutableMap<K, V>.() -> Unit): Map<K, V> {
    val map: MutableMap<K, V> = mutableMapOf()
    map.builder()
    return map
}

fun <T> buildList(builder: MutableList<T>.() -> Unit): List<T> {
    val list: MutableList<T> = mutableListOf()
    list.builder()
    return list
}