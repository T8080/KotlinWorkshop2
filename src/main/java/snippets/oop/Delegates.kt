package snippets.oop

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ClassWithDelegates() {
    var property: Int by DebugProperty(0)
}

class DebugProperty<T>(var value: T) : ReadWriteProperty<Any, T> {
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        println("setting value to $value")
        this.value = value
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        println("retrieve value: $value")
        return value
    }
}

fun main() {
    val x = ClassWithDelegates()
    x.property = 10
    println(x.property)
}