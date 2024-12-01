package year20.day04

import AoCResultPrinter
import Reader

const val year: Int = 20
const val day: Int = 4

val REQUIRED_FIELDS: List<String> = listOf(
    "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"
)

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val passports = getPassports(lines)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(passports, REQUIRED_FIELDS)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(passports)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPassports(lines: List<String>): List<Map<String, String>> {
    val passports: MutableList<Map<String, String>> = mutableListOf()
    var currentPassPort: MutableMap<String, String> = mutableMapOf()
    for (line in lines) {
        if (line.isBlank()) {
            passports.add(currentPassPort)
            currentPassPort = mutableMapOf()
            continue
        }
        val pairs = line.split("\\s+".toRegex())
        pairs.filterNot { pair -> pair.isEmpty() }
            .associate { pair -> pair.substringBefore(":") to pair.substringAfter(":") }
            .forEach { pair -> currentPassPort[pair.key] = pair.value }
    }
    if (currentPassPort.isNotEmpty()) {
        passports.add(currentPassPort)
    }
    return passports
}



fun getPart1Result(passports: List<Map<String, String>>, requiredFields: List<String>): Int =
    passports.count { passport ->
        passport.keys.containsAll(requiredFields)
    }

fun getPart2Result(passports: List<Map<String, String>>): Int {
    return passports.count { passport ->
        isValidPassport(passport) &&
                isValidBirthYear(passport["byr"]!!) &&
                isValidIssueYear(passport["iyr"]!!) &&
                isValidExpirationYear(passport["eyr"]!!) &&
                isValidHeight(passport["hgt"]!!) &&
                isValidHairColor(passport["hcl"]!!) &&
                isValidEyeColor(passport["ecl"]!!) &&
                isValidPassportID(passport["pid"]!!)
    }
}

fun isValidPassport(passport: Map<String, String>): Boolean =
    passport.keys.containsAll(REQUIRED_FIELDS)

fun isYear(string: String): Boolean {
    return string.matches("[0-9]{4}".toRegex())
}

fun isValidBirthYear(birthYear: String): Boolean {
    if (!isYear(birthYear)) {
        return false
    }
    val birthYearInt = birthYear.toInt()
    return birthYearInt in 1920..2002
}

fun isValidIssueYear(issueYear: String): Boolean {
    if (!isYear(issueYear)) {
        return false
    }
    val issueYearInt = issueYear.toInt()
    return issueYearInt in 2010..2020
}

fun isValidExpirationYear(expirationYear: String): Boolean {
    if (!isYear(expirationYear)) {
        return false
    }
    val issueYearInt = expirationYear.toInt()
    return issueYearInt in 2020..2030
}

fun isValidHeight(heightString: String): Boolean {
    if (heightString.matches("[0-9]{3}cm".toRegex())) {
        val centimeters: Int = heightString.substringBefore("cm").toInt()
        return centimeters in 150 .. 193
    }
    if (heightString.matches("[0-9]{2}in".toRegex())) {
        val inches: Int = heightString.substringBefore("in").toInt()
        return inches in 59 .. 76
    }
    return false
}

fun isValidHairColor(hairColor: String): Boolean {
    return hairColor.matches("#[0-9a-f]{6}".toRegex())
}

val VALID_EYE_COLORS = listOf(
    "amb",
    "blu",
    "brn",
    "gry",
    "grn",
    "hzl",
    "oth")

fun isValidEyeColor(eyeColor: String): Boolean = VALID_EYE_COLORS.contains(eyeColor)

fun isValidPassportID(passportId: String): Boolean =
    passportId.matches("[0-9]{9}".toRegex())
