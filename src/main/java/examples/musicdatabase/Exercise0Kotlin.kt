package snippets.interop

import examples.musicdatabase.Exercise0
import examples.musicdatabase.Length
import examples.musicdatabase.Track

fun main() {
    val database: Array<Track?> = arrayOfNulls(Exercise0.MAX_TRACKS)
    Exercise0.readDatabase(database)
    val tracks: List<Track> = database.filterNotNull()

    while (true) {
        val arguments = readLine()!!.split(" ")

        when (arguments[0]) {
            "track" -> tracks
                .filter { it.title.contains(arguments[1], ignoreCase = true) }
                .forEach { println("${it.title} ${it.time} ${it.tags}") }

            "artist" -> tracks
                .filter { it.artist.contains(arguments[1], ignoreCase = true) }
                .distinctBy { it.artist }
                .forEach { println(it.title) }

            "cds" -> tracks
                .filter { it.artist.contains(arguments[1], ignoreCase = false) }
                .distinctBy { it.cd }
                .forEach { println("${it.title} ${it.cd}") }

            "#cds" -> tracks
                .distinctBy { it.cd }
                .count()
                .also { println("$it cds") }

            "time" -> tracks
                .fold(Length()) { l, t -> Exercise0.lengthSum(l, t.time) }
                .also { println("total time: ${Exercise0.formatLength(it)}") }

            "stop" -> break

            else -> println("unrecognised command")
        }
    }
}