package snippets.dsl

import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalDateTime.now

fun main() {
    val start = LocalDateTime.of(2020, 0, 1, 1, 1)
    val end = LocalDateTime.of(2020, 1, 1, 1, 1)

    now() + 1.days + 1.minutes in start..end
}

val Int.minutes: Duration get() = Duration.ofMinutes(this.toLong())
val Int.days: Duration get() = Duration.ofDays(this.toLong())

