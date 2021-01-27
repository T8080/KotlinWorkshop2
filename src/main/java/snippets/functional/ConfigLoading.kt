package snippets.functional

import java.io.File

fun main() {
    val config: Map<String, String> = loadConfig("config.txt")
}

fun loadConfig(filename: String): Map<String, String> {
    val config = mutableMapOf<String, String>()

    for (line in File(filename).readLines()) {
        val (key, value) = line.split("=")
        config[key] = value
    }

    return config
}

fun loadConfig2(fileName: String): Map<String, String> = buildMap {
    for (line in File(fileName).readLines()) {
        val (key, value) = line.split("=")
        put(key, value)
    }
}

fun loadConfig3(fileName: String): Map<String, String> =
    File(fileName)
        .readLines()
        .map { it.split("=") }
        .associate { it[0] to it[1] }