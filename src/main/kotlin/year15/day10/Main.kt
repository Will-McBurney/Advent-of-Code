package year15.day10


const val puzzle_input = "1113122113"
var part2Memo: String = ""

fun main() {
    val startTime = System.currentTimeMillis()
    val getPart1Result = getPart1Result(puzzle_input)
    val getPart2Result = getPart2Result(part2Memo)
    val endTime = System.currentTimeMillis()
    println(
        """
        |Part One: $getPart1Result 
        |Part Two: $getPart2Result
        |Calculation time - ${endTime - startTime}ms
        |""".trimMargin()
    )
}



fun getPart1Result(puzzleInput: String): Int {
    var string = puzzleInput
    for (i in 1..40) {
        string = seeSayNumberString(string)
    }
    part2Memo = string
    return string.length
}

fun getPart2Result(part2Memo:String): Int {
    var string = part2Memo
    for (i in 41..50) {
        string = seeSayNumberString(string)
    }
    return string.length
}

fun seeSayNumberString(input: String): String {
    var count = 0
    var currentDigit = ' '
    val outputBuilder = StringBuilder(2 * input.length)
    val charArray = input.toCharArray()
    for (index in charArray.indices) {
        if (currentDigit == ' ') {
            currentDigit = charArray[index]
        }
        if (charArray[index] == currentDigit) {
            count++
            continue
        }
        outputBuilder.append(count.toString() + currentDigit.toString())
        currentDigit = charArray[index]
        count = 1
    }
    outputBuilder.append(count.toString() + currentDigit.toString())
    return outputBuilder.toString()
}

