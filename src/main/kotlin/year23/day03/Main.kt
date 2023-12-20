package year23.day03


import kotlin.streams.asStream

lateinit var lines: List<String>
lateinit var grid: CharGrid
var lineLength: Int = -1
var lineCount: Int = -1

fun main() {
    val startTime = System.currentTimeMillis()
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()


    lines = reader.readLines()
    grid = CharGrid(lines)
    lineLength = grid.width
    lineCount = grid.height

    val getPart1Result = getPart1Result()
    val getPart2Result = getPart2Result()
    val endTime = System.currentTimeMillis()
    println(
        """Part One: $getPart1Result 
        |Part Two: $getPart2Result 
        |Calculation time - ${endTime - startTime}ms
        |""".trimMargin()
    )
}

fun getPart1Result(): Int {
    return lines.asSequence()
        .mapIndexed{ index, line -> getSpecNumbersFromLine(line, index) }
        .asStream().parallel()
        .flatMap (List<SpecNumber>::stream)
        .filter { hasAdjacentSymbol(it) }
        .mapToInt(SpecNumber::value)
        .sum()
}

const val numberMatchPattern = "[0-9]+"
fun getSpecNumbersFromLine(line: String, lineNumber: Int): List<SpecNumber> {
    var match = Regex(numberMatchPattern).find(line)
    val specNumbers = arrayListOf<SpecNumber>()
    while (match != null) {
        specNumbers.add(SpecNumber(lineNumber, match.range.first, match.range.last, match.value.toInt()))
        match = match.next()
    }
    return specNumbers.filter { hasAdjacentSymbol(it) }
}

fun hasAdjacentSymbol(specNumber: SpecNumber): Boolean {
    return specNumber.getNeighbors(grid)
        .map { grid.get(it.first, it.second) }
        .any { it != '.' }
}

fun getPart2Result(): Int {
    val coordinateSpecNumberMap = lines.asSequence()
        .mapIndexed{index, line -> getSpecNumbersFromLine(line, index)}
        .flatten()
        .map { specNumber ->
            specNumber.getCoordinates().associateWith { specNumber }
        }
        .flatMap { it.asSequence() }
        .associate { it.key to it.value }


    return (0 ..< grid.height).map {
        row -> (0..< grid.width).map {
            column -> Pair(row, column)
        }
    }
        .flatten()
        .filter { grid.get(it.first, it.second) == '*'}
        .sumOf { getGearRatio(it.first, it.second, coordinateSpecNumberMap) }
}

fun getGearRatio(row: Int, column: Int, coordinateSpecNumberMap: Map<Pair<Int, Int>, SpecNumber>): Int {
    val adjacentSet = grid.getNeighborsCoordinates(row, column)
        .filter{isDigit(grid.get(it.first, it.second)) }
        .map{ getSpecNumber(it.first, it.second, coordinateSpecNumberMap) }
        .toSet()

    if (adjacentSet.size != 2) {
        return 0
    }
    return adjacentSet
        .map(SpecNumber::value)
        .reduce(Int::times)
}

fun getSpecNumber(row: Int, column: Int, coordinateSpecNumberMap: Map<Pair<Int, Int>, SpecNumber>): SpecNumber =
    coordinateSpecNumberMap[Pair(row, column)]!!


fun isDigit(char: Char): Boolean {
    return char in '0'..'9'
}