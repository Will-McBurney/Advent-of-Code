package year23.day18

import java.awt.Color

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val commands = getCommands(lines)
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(commands)
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

/**fun buildLoop(commands: List<Command>): Loop {
    val loop = Loop()
    commands.forEach { command -> loop.addEdge(command.direction, command.distance, command.color) }
    return loop
}*/

fun getDirection(directionString: String): Direction {
    return when(directionString) {
        "U" -> Direction.UP
        "D" -> Direction.DOWN
        "R" -> Direction.RIGHT
        "L" -> Direction.LEFT
        else -> throw IllegalArgumentException("Bad direction string -> $directionString")
    }
}

fun getPart1Result(commands: List<Command>): Int {
    var coordinate = Coordinate(0, 0)
    var perimeter = 0
    var area = 0
    for(command in commands) {
        val newRow = coordinate.row + (command.direction.dRow * command.distance)
        val newCol = coordinate.col + (command.direction.dCol * command.distance)
        coordinate = Coordinate(newRow, newCol)

        perimeter += command.distance
        area += coordinate.col * (command.direction.dRow * command.distance)
    }
    return area + (perimeter / 2) + 1
}

fun getPart2Result(): Int {
    return 0
}