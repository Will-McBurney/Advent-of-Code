package year15.day5

import java.lang.RuntimeException

const val vowelsList = "aeiou"
val part = 2

fun main() {
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val text = reader.readText();
    val startTime = System.currentTimeMillis();
    val result = when (part) {
        1 -> getNiceStringCount_part1(text)
        2 -> getNiceStringCount_part2(text)
        else -> throw RuntimeException()
    }
    val endTime = System.currentTimeMillis();
    print("Answer: $result - Calculation time - ${endTime - startTime}ms")
}

fun getNiceStringCount_part1(text: String): Long {
    return text.lines().stream()
        .filter{ hasThreeVowels(it) }
        .filter{ hasDoubleLetter(it) }
        .filter{ !containsNaughtyString(it) }
        .count()
}

fun getNiceStringCount_part2(text: String): Long {
    return text.lines().stream()
        .filter{ hasDuplicateTwoLetterSequence(it) }
        .filter{ hasSandwich(it) }
        .count()
}

fun hasThreeVowels(input: String): Boolean {
    return 3 <= input.toCharArray()
        .count { vowelsList.contains(it) }
}

fun hasDoubleLetter(input: String): Boolean {
    val charArray = input.toCharArray()
    for (index in 0..<input.length - 1) {
        if (charArray[index] == charArray[index + 1]) {
            return true;
        }
    }
    return false;
}

val naughtyStrings = listOf("ab", "cd", "pq", "xy")

fun containsNaughtyString(input: String): Boolean {
    return naughtyStrings.stream().anyMatch{input.contains(it)}
}

fun hasDuplicateTwoLetterSequence(input: String): Boolean {
    for (targetIndex in 0 .. input.length - 2) {
        val target = input.substring(targetIndex, targetIndex+2)
        for (searchIndex in targetIndex + 2 .. input.length - 2) {
            if (target == input.substring(searchIndex, searchIndex + 2)) {
                return true
            }
        }
    }
    return false;
}

fun hasSandwich(input: String): Boolean {
    val charArray = input.toCharArray();
    for (targetIndex in 0 .. input.length - 3) {
        if (charArray[targetIndex] == charArray[targetIndex + 2]) {
            return true;
        }
    }
    return false;
}

