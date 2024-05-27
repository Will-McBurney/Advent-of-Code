package year20.day05

import AoCResultPrinter
import Reader
const val year: Int = 20
const val day: Int = 5

data class SeatString(
    val rowString: String,
    val columnString: String
) {
    constructor(string: String) : this(
        rowString = string.slice(0 ..< 7),
        columnString = string.slice(7 ..< 10)
    )

    fun getRowNumber(): Int {
        var low = 0
        var high = 128
        rowString.forEach { char ->
            when(char) {
                'F' -> high = (low + high) / 2
                'B' -> low = (low + high) / 2
                else -> throw IllegalArgumentException()
            }
        }
        return low
    }

    fun getColumnNumber(): Int {
        var low = 0
        var high = 8
        columnString.forEach { char ->
            when(char) {
                'L' -> high = (low + high) / 2
                'R' -> low = (low + high) / 2
                else -> throw IllegalArgumentException()
            }
        }
        return low
    }

    fun getSeatId(): Int {
        return getRowNumber() * 8 + getColumnNumber()
    }
}


fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val seatStrings = lines.map { string -> SeatString(string) }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(seatStrings)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(seatStrings)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(seatStrings: List<SeatString>): Int {
    return seatStrings.maxOf { seatString -> seatString.getSeatId() }
}

fun getPart2Result(seatStrings: List<SeatString>): Int {
    val seatIds = seatStrings.map { it.getSeatId() }.toSet()
    val minId = seatIds.min()
    val maxId = seatIds.max()
    (minId + 1 ..< maxId).forEach { id ->
        if (!seatIds.contains(id)) return id
    }
    return -1
}
