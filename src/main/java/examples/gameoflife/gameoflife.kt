package examples.gameoflife

typealias Universe = List<List<Boolean>>

const val gliderString = """
......................
......................
......................
......................
......................
..***.................
....*.................
...*..................
......................
"""

fun universeOf(str: String): Universe =
    str.lines().map { lines ->
        lines.map { it == '*' }
    }

fun Universe.toStr(): String =
    joinToString("\n") { line ->
        line.joinToString("") { if (it) "*" else "." }
    }

fun Universe.neighbourCount(x: Int, y: Int): Int =
    ((y - 1)..(y + 1)).map { yy ->
        ((x - 1)..(x + 1)).map { xx ->
            if (getOrNull(yy)?.getOrNull(xx) == true && !(xx == x && y == yy)) 1 else 0
        }.sum()
    }.sum()

fun Universe.nextGeneration(): Universe =
    mapIndexed { y, row ->
        row.mapIndexed { x, cell ->
            neighbourCount(x, y).let { n ->
                n == 3 || (n == 2 && cell)
            }
        }
    }

fun main() {
    var universe = universeOf(gliderString)
    repeat(100) {
        println(universe.toStr())
        universe = universe.nextGeneration()
        Thread.sleep(100)
    }
}




