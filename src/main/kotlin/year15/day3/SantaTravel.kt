package year15.day3

import java.lang.RuntimeException

val part = 2

fun main() {
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val text = reader.readText();
    val startTime = System.currentTimeMillis();
    val result = when (part) {
        1 -> getHousesVisited1(text)
        2 -> getHousesVisited2(text)
        else -> throw RuntimeException()
    }
    val endTime = System.currentTimeMillis();
    print("Answer: $result - Calculation time - ${endTime - startTime}ms")
}

fun getHousesVisited1(input: String): Int {
    val santa = Santa()
    input.toCharArray().forEach { santa.move(it) }
    return santa.getHousesVisited()
}

fun getHousesVisited2(input: String): Int {
    val santa = Santa()
    val roboSanta = Santa()
    val visited = hashSetOf<Coordinate>()
    val charArray = input.toCharArray()
    for (index in charArray.indices) {
        if (index % 2 == 0) {
            santa.move(charArray[index])
            visited.add(santa.getPosition())
        } else {
            roboSanta.move(charArray[index])
            visited.add(roboSanta.getPosition())
        }
    }
    return visited.size
}
