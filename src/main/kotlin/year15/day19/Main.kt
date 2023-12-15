package year15.day19

lateinit var reverseMap: Map<String, String>
fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val replacementMap = getReplacements(lines)
    println(replacementMap)
    val text = lines.last()
    println(text)
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(text, replacementMap)
    val part1EndTime = System.currentTimeMillis()
    reverseMap = getReverseReplacements(lines)
    println(reverseMap.toList().sortedBy { it.first })

    //Do Part 2
    val part2Result = getPart2Result(text)
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

fun getReplacements(lines: List<String>): Map<String, List<String>> {
    val replacementsMap: MutableMap<String, MutableList<String>> = mutableMapOf()
    for (line in lines) {
        if (line.isEmpty())
            break
        val split = line.split(" => ")
        if (replacementsMap.containsKey(split[0])) {
            replacementsMap[split[0]]!!.add(split[1])
        } else {
            replacementsMap[split[0]] = mutableListOf<String>(split[1])
        }
    }
    return replacementsMap
}

fun getReverseReplacements(lines: List<String>): Map<String, String> {
    val replacementsMap: MutableMap<String, String> = mutableMapOf()
    for (line in lines) {
        if (line.isEmpty())
            break
        val split = line.split(" => ")
        if (replacementsMap.containsKey(split[1])) {
            println("Houston we have a problem")
        } else {
            replacementsMap[split[1]] = split[0]
        }
    }
    return replacementsMap
}

fun getPart1Result(text: String, replacementMap: Map<String, List<String>>): Int {
    val outputSet: MutableSet<String> = mutableSetOf()
    replacementMap.keys.forEach { key ->
        var startingIndex = 0
        while (text.substring(startingIndex).contains(key)) {
            val replacementIndex = startingIndex + text.substring(startingIndex).indexOf(key)
            replacementMap[key]!!.forEach {
                outputSet.add(text.substring(0, replacementIndex) + it + text.substring(replacementIndex + key.length))
            }
            startingIndex = replacementIndex + 1

        }
    }
    return outputSet.size
}

fun getPart2Result(target: String): Int {
    val counts = mutableListOf<Int>()
    repeat(100) {
        var currentString = target
        var count = 0
        repeat(250) {
            val keys = reverseMap.keys.toList()
            for (key in keys.shuffled()) {
                if (currentString.contains(key)) {
                    currentString = currentString.replaceFirst(key, reverseMap[key]!!)
                    count++
                    break
                }
            }
            if (currentString == "e") {
                println(currentString)
                counts.add(count)
            }
        }
        count = 0
    }
    println(counts)
    return counts.min()
}
//    var searchQueue: MutableList<Pair<String, Int>> = ArrayList()
//    searchQueue.add(Pair(target, 0))
//    while (true) {
//        val currentPair: Pair<String, Int> = searchQueue.first()
//        val currentString: String = currentPair.first
//        val currentSteps: Int = currentPair.second
//
//        if (currentString == "e") {
//            return currentSteps
//        }
//
//        val nextKey = replacementMap.keys.filter {currentString.contains(it) }
//            .forEach {
//                val nextIndex: Int = currentString.indexOf(it)
//                val nextValue: String = replacementMap[it]!!
//                val newString: String =
//                    currentString.substring(0, nextIndex) + nextValue + currentString.substring(nextIndex + it.length)
//                if (searchQueue.none { it.first == newString }) {
//                    searchQueue.add(Pair(newString, currentSteps + 1))
//                }
//            }
//        searchQueue = searchQueue.distinct().sortedBy { it.first.length }.toMutableList()
//
//    }

