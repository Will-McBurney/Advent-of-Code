package year21.day08

import AoCResultPrinter
import Reader
const val year: Int = 21
const val day: Int = 8



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

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

val digitSementMap: Map<Int, Set<Char>> = mapOf(
    0 to setOf('a', 'b', 'c', 'e', 'f', 'g'),
    1 to setOf('c', 'f'),
    2 to setOf('a', 'c', 'd', 'e', 'g'),
    3 to setOf('a', 'c', 'd', 'f', 'g'),
    4 to setOf('b', 'c', 'd', 'f'),
    5 to setOf('a', 'b', 'd', 'f', 'g'),
    6 to setOf('a', 'b', 'd', 'e', 'f', 'g'),
    7 to setOf('a', 'c', 'f'),
    8 to setOf('a', 'b', 'c', 'd', 'e', 'f', 'g'),
    9 to setOf('a', 'b', 'c', 'd', 'f', 'g'),
)

val part1TargetLengths = setOf(
    digitSementMap[1]!!.size,
    digitSementMap[4]!!.size,
    digitSementMap[7]!!.size,
    digitSementMap[8]!!.size
)
fun getPart1Result(lines: List<String>): Int {
    return lines.map { line -> line.substringAfter("|").trim().split(" ") }
        .flatten()
        .count { str -> part1TargetLengths.contains(str.length) }
}

fun getPart2Result(lines: List<String>): Int {
    var sum = 0

    for (line in lines) {
        val charMappings: MutableMap<Char, Char> = getCharMappings(line)

        val answerTokens = line.substringAfter(" | ")
            .trim()
            .split(" ")
            .map { it.toCharArray().sorted() }

        val answer = getAnswerAsInt(answerTokens, charMappings)
        sum += answer
    }
    return sum
}

private fun getAnswerAsInt(
    answerTokens: List<List<Char>>,
    charMappings: MutableMap<Char, Char>
): Int {
    val answer = answerTokens.map { str ->
        val trueCharSet = str.map { ch -> charMappings[ch]!! }.toSet()
        digitSementMap.entries.single { e -> e.value == trueCharSet }.key
    }.joinToString("").toInt()
    return answer
}

private fun getCharMappings(line: String): MutableMap<Char, Char> {
    val charMappings: MutableMap<Char, Char> = mutableMapOf()
    val unusedChars: MutableSet<Char> = digitSementMap[8]!!.toMutableSet()

    val hintTokens = line.substringBefore(" | ")
        .trim()
        .split(" ")

    val twoLength = hintTokens.single { it.length == 2 }.toCharArray().toSet()
    val threeLength = hintTokens.single { it.length == 3 }.toCharArray().toSet()
    val fourLength = hintTokens.single { it.length == 4 }.toCharArray().toSet()

    val charFrequencies = hintTokens.joinToString("")
        .toCharArray().fold(mutableMapOf<Char, Int>()) { map, item ->
            map[item] = 1 + (map[item] ?: 0)
            map
        }

    // e is the only one with 4 instances
    val eChar = charFrequencies.entries.single { e -> e.value == 4 }.key
    charMappings[eChar] = 'e'
    unusedChars.remove(eChar)

    // f is the only one with 9 instances
    val fChar = charFrequencies.entries.single { e -> e.value == 9 }.key
    charMappings[fChar] = 'f'
    unusedChars.remove(fChar)

    // b is the only one with 6 instances
    val bChar = charFrequencies.entries.single { e -> e.value == 6 }.key
    charMappings[bChar] = 'b'
    unusedChars.remove(bChar)

    // a is the only character in twoLength ("1") but not three length ("7")
    val aChar = (threeLength - twoLength).single()
    charMappings[aChar] = 'a'
    unusedChars.remove(aChar)

    // c is the only character left in twoLength
    val cChar = twoLength.single { unusedChars.contains(it) }
    charMappings[cChar] = 'c'
    unusedChars.remove(cChar)

    // d is only unused character in foiur
    val dChar = fourLength.single { unusedChars.contains(it) }
    charMappings[dChar] = 'd'
    unusedChars.remove(dChar)

    //g is the last character
    charMappings[unusedChars.single()] = 'g'
    return charMappings
}
