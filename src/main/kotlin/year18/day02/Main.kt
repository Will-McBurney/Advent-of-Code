package year18.day02

import AoCResultPrinter

const val year: Int = 18
const val day: Int = 2

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
        .filter { it.isNotEmpty() }
        .map { it.trim() }

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(lines)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(lines)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(lines: List<String>): Int {
    val charCounts = lines.map { getLetterCounts(it) }
    return charCounts.count{ map -> map.values.contains(2)} *
            charCounts.count{ map -> map.values.contains(3)}
}

fun getLetterCounts(string: String): Map<Char, Int> {
    return string.fold(mutableMapOf<Char, Int>()) {map, char ->
        map[char] = 1 + map.getOrDefault(char, 0)
        map
    }
}

fun getPart2Result(lines: List<String>): String {
    lines.forEachIndexed { index, a ->
        lines.subList(index + 1, lines.size).forEach { b ->
            if (getDifferenceCount(a, b) == 1) {
                return getSameString(a, b)
            }
        }
    }
    return "Not Found"
}

fun getDifferenceCount(a: String, b: String): Int {
    assert(a.length == b.length)
    return a.indices.count {index -> a[index] != b[index]}
}

fun getSameString(a: String, b: String): String {
    assert(a.length == b.length)
    return a.filterIndexed {index, _ -> a[index] == b[index]}

}
