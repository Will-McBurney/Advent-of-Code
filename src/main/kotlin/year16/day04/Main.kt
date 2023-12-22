package year16.day04

import AoCResultPrinter
import Reader

const val year: Int = 16
const val day: Int = 4

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val rooms = getRooms(lines)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(rooms)
    printer.endPart1()

    //Do Part 2
    val target = "northpole object storage"
    val part2Result = getPart2Result(rooms, target)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

val roomRegex: Regex = "([a-z-]+)([0-9]{3})\\[([a-z]{5})]".toRegex()

fun getRooms(lines: List<String>): List<Room> {
    return lines.map(String::trim)
        .map { line -> roomRegex.find(line)!!.groupValues }
        .map { groups -> Room(groups[1], groups[2].toInt(), groups[3])}
}

fun getPart1Result(rooms: List<Room>): Int {
    return rooms.filter(Room::isRealRoom)
        .sumOf(Room::number)
}

fun getPart2Result(rooms: List<Room>, target: String): Int {
    return rooms.first {room -> encryptCaesar(room.label, room.number) == target }
        .number
}

fun encryptCaesar(input: String, shifts: Int): String {
    return input.split("-")
        .joinToString(" ") { token ->
            token.map { c -> c.code - 'a'.code }
            .map { c -> (c + (shifts % 26)) % 26 }
            .map { c -> (c + 'a'.code).toChar() }
            .joinToString("")
        }.trim()

}