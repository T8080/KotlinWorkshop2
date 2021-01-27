package snippets.oop

import snippets.functional.loadConfig
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ConfigDelegate(fileName: String) : ReadOnlyProperty<Any, String> {
    private val config by lazy { loadConfig(fileName) }

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return config[property.name]!!
    }
}

object Settings {
    private val config = ConfigDelegate("config.txt")

    val a by config
    val b by config
    val c by config
}

fun main() {
    println(Settings.a)
    println(Settings.b)
    println(Settings.c)
}