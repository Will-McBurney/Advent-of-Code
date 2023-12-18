package year23.day18

import java.awt.Color

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("test_input2.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val commands = getCommands(lines)
    val loop = buildLoop(commands)
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(loop)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result()
    val part2EndTime = System.currentTimeMillis()

    //Display output
    println(
        """
        |Read Time: %10d ms
        |
        |Part One:  %10d - Time %6d ms
        |Part Two:  %10d - Time %6d ms
        |
        |Total time - ${part2EndTime - startTime}ms
        |""".trimMargin().format(readEndTime - startTime,
            part1Result,
            part1EndTime - readEndTime,
            part2Result,
            part2EndTime - part1EndTime)
    )
}

val linePattern = "([RDLU]) ([0-9])+ \\((#[0-9a-f]{6})\\)".toRegex()

fun getCommands(lines: List<String>): List<Command> {
    return lines.map{line:String -> linePattern.find(line)!!.groupValues}
        .map{groups -> Command(getDirection(groups[1]), groups[2].toInt(), Color.decode(groups[3].uppercase()))}
}

fun buildLoop(commands: List<Command>): Loop {
    val loop = Loop()
    commands.forEach { command -> loop.addEdge(command.direction, command.distance, command.color) }
    return loop
}

fun getDirection(directionString: String): Direction {
    return when(directionString) {
        "U" -> Direction.UP
        "D" -> Direction.DOWN
        "R" -> Direction.RIGHT
        "L" -> Direction.LEFT
        else -> throw IllegalArgumentException("Bad direction string -> $directionString")
    }
}

fun getPart1Result(loop: Loop): Int {
    val rowRange = loop.getRowRange()
    val columnRange = loop.getColumnRange()
    var count = 0 //counts spaces inside the loop, but not on the loop
    rowRange.forEach { row ->
        var insideLoop = false
        columnRange.forEach { col ->
            val coordinate = Coordinate(row, col)
            val aboveCoordinate = Coordinate(row -1, col)
            if (loop.contains(coordinate) && loop.contains(aboveCoordinate)) {
                insideLoop = !insideLoop
            }
            if (!loop.contains(coordinate) && insideLoop) {
                count++
            }
        }
    }

    return count + loop.size
}

fun getPart2Result(): Int {
    return 0
}