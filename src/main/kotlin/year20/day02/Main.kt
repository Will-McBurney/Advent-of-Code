package year20.day02

import AoCResultPrinter
import Reader

const val year: Int = 20
const val day: Int = 2


fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)

    val policies: List<PasswordPolicy> = lines.map{ it.substringBefore(":")}
        .map { it.split("-", " ") }
        .map { PasswordPolicy(it[0].toInt(), it[1].toInt(), it[2][0]) }
    val passwords: List<String> = lines.map { it.substringAfter(":").trim() }

    println(policies)
    println(passwords)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(passwords, policies)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(passwords, policies)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

data class PasswordPolicy(
    val minimumCount: Int,
    val maximumCount: Int,
    val character: Char
) {
    private val range: IntRange = minimumCount..maximumCount

    fun isMetByPart1(password: String): Boolean {
        val count = password.count { it == character }
        return count in range
    }

    fun isMetByPart2(password: String): Boolean {
        val firstPositionChar = password[minimumCount - 1]
        val secondPositionChar = password[maximumCount - 1]
        return (firstPositionChar == character) xor (secondPositionChar == character)
    }
}

fun getPart1Result(passwords: List<String>, policies: List<PasswordPolicy>): Int {
    return passwords.indices.count { index -> policies[index].isMetByPart1(passwords[index]) }
}

fun getPart2Result(passwords: List<String>, policies: List<PasswordPolicy>): Int {
    return passwords.indices.count { index -> policies[index].isMetByPart2(passwords[index]) }
}
