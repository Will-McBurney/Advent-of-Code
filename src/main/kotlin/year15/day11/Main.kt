package year15.day11

const val PUZZLE_INPUT = "hxbxwxba"

fun main() {
    val startTime = System.currentTimeMillis()
    val part1Result = getNextPassword(PUZZLE_INPUT)
    val part2Result = getNextPassword(part1Result)
    val endTime = System.currentTimeMillis()
    println(
        """
        |Part One: $part1Result 
        |Part Two: $part2Result 
        |Calculation time - ${endTime - startTime}ms
        |""".trimMargin()
    )
}

fun getNextPassword(startingPassword: String): String {
    val charArray = startingPassword.toCharArray()
    bruteForce(charArray)
    return charArray.concatToString()
}

fun bruteForce(input: CharArray) {
    do  {
        incrementString(input)
    } while (!isValidPassWord(input))
    println(input.toString())
}

fun isValidPassWord(input: CharArray): Boolean {
    return hasNoOil(input) &&
            hasIncreasingLetterTriple(input) &&
            hasTwoDoubleLetters(input)
}

fun incrementString(input: CharArray) {
    var carry = false
    var currentBit = input.size - 1
    do {
        carry = false
        if (input[currentBit] == 'z') {
            input[currentBit] = 'a'
            carry = true
            currentBit--
            if (currentBit == -1) {
                return
            }
        } else {
            input[currentBit]++
        }
    } while (carry)
}

fun hasIncreasingLetterTriple(input: CharArray): Boolean {
    for (index in 0.. input.size - 3) {
        val firstLetter = input[index]
        if (input[index + 1] == firstLetter + 1 && input[index + 2] == firstLetter + 2) {
            return true
        }
    }
    return false
}

fun hasNoOil(input: CharArray): Boolean {
    return !(input.contains('o') || input.contains('i') || input.contains('l'))
}

fun hasTwoDoubleLetters(input: CharArray): Boolean {
    var doubleLetterCount = 0
    var index = 0
    while(index < input.size - 1) {
        if (input[index] == input[index + 1]) {
            doubleLetterCount++
            index++
        }
        if (doubleLetterCount >= 2) {
            return true
        }
        index++
    }
    return false
}
