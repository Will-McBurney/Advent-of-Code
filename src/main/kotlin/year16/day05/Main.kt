package year16.day05

import AoCResultPrinter
import Reader
import java.security.MessageDigest

const val year: Int = 16
const val day: Int = 5


const val testInput: String = "abc";
const val input: String = "uqwqemis";

fun main() {
    val printer = AoCResultPrinter(year, day)
    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(input);
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(input)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getPart1Result(doorID: String): String {
    var index = 0;
    var password: String = "";
    while (password.length < 8) {
        val hashInput = doorID + index
        val hash = md5(hashInput)
        if (hash.startsWith("00000")) {
            password += hash[5]
        }
        index++;
    }
    return password;
}

fun getPart2Result(doorID: String): String {
    var index = 0;
    var password: CharArray = CharArray(8){'\u0000'};
    while (password.contains('\u0000')) {
        val hashInput = doorID + index
        val hash = md5(hashInput)
        if (hash.startsWith("00000")) {
            val position = hash[5] - '0';
            if ( 0 <= position && position <= 7 && password[position] == '\u0000') {
                password[position] = hash[6]
            }
        }
        index++;
    }
    return password.joinToString("");
}

@OptIn(ExperimentalStdlibApi::class)
fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(input.toByteArray())
    return digest.toHexString()
}