package year23.day15

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val input = processInput(lines)
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(input)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result(input)
    val part2EndTime = System.currentTimeMillis()

    //Display output
    println(
        """
        |Read Time: %10d ms
        |
        |Part One:  %10d - Time %6d ms
        |Part Two:  %10d - Time %6d ms
        |
        |Total time - ${part2EndTime - startTime}ms
        |""".trimMargin().format(readEndTime - startTime,
            part1Result,
            part1EndTime - readEndTime,
            part2Result,
            part2EndTime - part1EndTime)
    )
}

fun processInput(lines: List<String>): List<String> {
    return lines[0].trim().split(",")
}

fun getPart1Result(input: List<String>): Int {
    return input.map(::hashString)
        .sum()
}

fun getPart2Result(input: List<String>): Int {
    val array = Array<MutableList<Pair<String, Int>>>(256){ mutableListOf() }
    input.forEach { string ->
        if (string.contains("=")) {
            val split = string.split("=")
            val label = split[0].trim()
            val number = split[1].trim().toInt()
            val hash = hashString(label)
            val list = array[hash]
            val toChange = list.indexOfFirst { it.first == label }
            if (toChange >= 0) {
                list.removeAt(toChange)
                list.add(toChange, Pair(label, number))
            } else {
                list.add(Pair(label, number))
            }
        } else if (string.contains("-")) {
            val label = string.split("-")[0]
            val hash = hashString(label)
            val list = array[hash]
            try {
                val toRemove = list.first { it.first == label }
                list.remove(toRemove)
            } catch (ignored: NoSuchElementException) {  }
        }
    }

    for(i in 0 .. 4) { println(array[i]) }

    return array.mapIndexed { arrayIndex, list ->
        (1 + arrayIndex) * list.mapIndexed { listIndex, lens ->
            ((1 + listIndex) * lens.second)
        }.sum()
    }.sum()
}

fun hashString(input: String): Int {
    var currentValue = 0
    input.forEach {char ->
        currentValue += char.code
        currentValue *= 17
        currentValue %= 256
    }
    return currentValue
}