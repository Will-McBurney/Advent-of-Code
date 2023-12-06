package year15.day12

import org.json.JSONArray
import org.json.JSONObject


fun main() {
    val startTime = System.currentTimeMillis()
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val jsonRoot = JSONObject(reader.readText())
    val part1Result = getPart1Result(jsonRoot)
    val part2Result = getPart2Result(jsonRoot)
    val endTime = System.currentTimeMillis()
    println(
        """
        |Part One: $part1Result 
        |Part Two: $part2Result 
        |Calculation time - ${endTime - startTime}ms
        |""".trimMargin()
    )
}

fun getPart1Result(root: JSONObject): Int {
    return getNumberSum(root)
}

fun getNumberSum(root: JSONObject): Int {
    var sum = 0
    for (key in root.keys()) {
        when (val thing = root.get(key)) {
            is Int -> sum += thing
            is JSONObject -> sum += getNumberSum(thing)
            is JSONArray -> sum += getNumberSum(thing)
        }
    }
    return sum
}

fun getNumberSum(array: JSONArray): Int {
    var sum = 0
    for (thing in array) {
        when (thing) {
            is Int -> sum += thing
            is JSONObject -> sum += getNumberSum(thing)
            is JSONArray -> sum += getNumberSum(thing)
        }
    }
    return sum
}

fun getPart2Result(root: JSONObject): Int {
    return getNumberSumIgnoreRed(root)
}

fun getNumberSumIgnoreRed(root: JSONObject): Int {
    if (root.keySet().stream()
            .anyMatch { it == "red" || (root.get( it ) is String && root.getString( it ) == "red" ) }) {
        return 0
    }
    var sum = 0
    for (key in root.keys()) {
        when (val thing = root.get(key)) {
            is Int -> sum += thing
            is JSONObject -> sum += getNumberSumIgnoreRed(thing)
            is JSONArray -> sum += getNumberSumIgnoreRed(thing)
        }
    }
    return sum
}

fun getNumberSumIgnoreRed(array: JSONArray): Int {
    var sum = 0
    for (thing in array) {
        when (thing) {
            is Int -> sum += thing
            is JSONObject -> sum += getNumberSumIgnoreRed(thing)
            is JSONArray -> sum += getNumberSumIgnoreRed(thing)
        }
    }
    return sum
}

