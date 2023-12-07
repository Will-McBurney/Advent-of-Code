package year15.day13

import java.io.BufferedReader

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val happinessMap = getHappinessMap(reader)
    println(happinessMap)
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = bruteForceHappiestScore(happinessMap)
    val part1EndTime = System.currentTimeMillis()

    addAmbivalentPersonTo(happinessMap)

    //Do Part 2
    val part2Result = bruteForceHappiestScore(happinessMap)
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
        |""".trimMargin().format(
            readEndTime - startTime,
            part1Result,
            part1EndTime - readEndTime,
            part2Result,
            part2EndTime - part1EndTime
        )
    )
}

fun addAmbivalentPersonTo(happinessMap: HappinessMap) {
    happinessMap.getPeople().forEach {
        happinessMap.add(it, "ME", 0)
        happinessMap.add("ME", it, 0)
    }
}

const val SEATING_LINE_PATTERN = "([A-Za-z]+) would (gain|lose) ([0-9]+) happiness units by sitting next to ([A-Za-z]+)\\."

fun getHappinessMap(reader: BufferedReader): HappinessMap {
    return reader.readLines()
        .asSequence()
        .map {it.trim()}
        .filter { it.isNotEmpty() }
        .mapNotNull { SEATING_LINE_PATTERN.toRegex().find(it) }
        .map { matchToList(it) }
        .fold(HappinessMap()) { map, it ->
            map.add(it.first, it.second, it.third)
            return@fold map
        }
}

fun matchToList(match: MatchResult): Triple<String, String, Int> {
    return if (match.groups[2]!!.value == "gain") {
        Triple(
            match.groups[1]!!.value,
            match.groups[4]!!.value,
            match.groups[3]!!.value.toInt()
        )
    } else {
        Triple(
            match.groups[1]!!.value,
            match.groups[4]!!.value,
            -1 * match.groups[3]!!.value.toInt()
        )
    }
}

fun bruteForceHappiestScore(happinessMap: HappinessMap): Int {
    val permutations = getSeatingPermutations(happinessMap.getPeople())
    return permutations.map { SeatingArrangement(it, happinessMap) }
        .maxOf { it.getNetHappiness() }
}

fun getSeatingPermutations(people: List<String>): List<List<String>> {
    val endingPerson = people[0]
    val permutations = getPermutations(people.subList(1, people.size))
    permutations.forEach{ it.add(endingPerson) }
    return permutations
}

fun getPermutations(people: List<String>): MutableList<MutableList<String>> {
    if (people.size == 1) {
        return mutableListOf(mutableListOf(people[0]))
    }
    val outputs = mutableListOf<MutableList<String>>()
    people.forEach { person ->
        val otherPeople = people.toList().filter{ it != person}
        val otherPermutations = getPermutations(otherPeople)
        otherPermutations.forEach { it.add(person) }
        otherPermutations.forEach { outputs.add(it) }
    }
    return outputs
}

fun getPart2Result(): Int {
    return 0
}
