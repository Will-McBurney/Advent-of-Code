package year24.day14

import AoCResultPrinter
import Reader
const val year: Int = 24
const val day: Int = 14

const val WIDTH = 101
const val HEIGHT = 103

data class Robot(
    var pX: Int,
    var pY: Int,
    val vX: Int,
    val vY: Int,
) {
    fun move() {
        pX = (pX + vX) % WIDTH
        pY = (pY + vY) % HEIGHT
        if (pX < 0) pX += WIDTH
        if (pY < 0) pY += HEIGHT
    }
}

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val robots = Reader.getLines(year, day, inputFilename)
        .map { Robot(
            it.substringAfter("p=").substringBefore(",").toInt(),
            it.substringAfter(",").substringBefore(" v=").toInt(),
            it.substringAfter("v=").substringBefore(",").toInt(),
            it.substringAfterLast(",").toInt()
        ) }


    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(robots.map { it.copy() }, 100)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(robots)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(robots: List<Robot>, seconds: Int): Long {
    repeat(seconds) { s ->
        if (s  % 103 == 40 && s % 101 == 99) {
            printGrid(robots, s)
            Thread.sleep(400)
        }
        robots.forEach {it.move()}
    }
    val safetyScores = mutableListOf<Long>()
    for (xRange in listOf(IntRange(0, WIDTH/2-1), IntRange(WIDTH/2 + 1, WIDTH-1))) {
        for (yRange in listOf(IntRange(0, HEIGHT/2-1), IntRange(HEIGHT/2 + 1, HEIGHT-1))) {

            safetyScores.add(
                robots.count{ r -> r.pX in xRange && r.pY in yRange }.toLong()
            )
        }
    }
    return safetyScores.reduce(Long::times)
    return 0

}

fun printGrid(robots: List<Robot>, secondsElapsed: Int) {
    println("Seconds Elapsed: + $secondsElapsed")
    for (i in 0 .. WIDTH) {
        for (j in 0 .. HEIGHT) {
            val count = robots.count{ r -> r.pX == i && r.pY == j }
            if (count == 0) {print(" ")} else {print("#")}
        }
        println()
    }
    println(secondsElapsed)
    println("--------------------")
    println()
}

fun getPart2Result(robots: List<Robot>): Int {
    var s = 0
    while (true) {
        if (s  % 103 == 40 && s % 101 == 99) {
            printGrid(robots, s)
            return s
        }
        robots.forEach {it.move()}
        s++
    }
}
