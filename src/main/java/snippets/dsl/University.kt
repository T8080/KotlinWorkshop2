package snippets.dsl

import kotlin.random.Random

data class University(
    val faculties: MutableList<Faculty> = mutableListOf()
)

data class Faculty(
    val name: String,
    val courses: MutableList<Course> = mutableListOf()
)

data class Course(
    val name: String,
    var ecs: Int = 3
)

fun university(config: University.() -> Unit) = University().apply(config)

fun University.faculty(name: String, config: Faculty.() -> Unit) = faculties.add(Faculty(name).apply(config))

fun Faculty.course(name: String, config: Course.() -> Unit = {}) = courses.add(Course(name).apply(config))



fun main() {
    val ru = university {
        faculty("NWI") {
            course("Object Oriented Programming")
            course("Functional Programming") {
                ecs = 6
            }
        }

        faculty("SOW") {
            for (i in 0..2) {
                course("Programming $i")
            }
        }
    }

    println(ru)
}