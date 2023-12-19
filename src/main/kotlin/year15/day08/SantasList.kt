package year15.day08


fun main() {
    val startTime = System.currentTimeMillis()
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val result1 = getPart1Result(lines)
    val result2 = getPart2Result(lines)
    val endTime = System.currentTimeMillis()
    print("""
        Part1: $result1
        Part2: $result2 
        Calculation time - ${endTime - startTime}ms"""
        .trimIndent())
}

fun getPart1Result(lines: List<String>): Int {
    return lines
        .map(String::trim)
        .filterNot(String::isEmpty)
        .sumOf { getStringCodeLength(it) - getStringMemoryLength(it) }
}

fun getPart2Result(lines: List<String>): Int {
    return lines
        .map(String::trim)
        .filterNot(String::isEmpty)
        .sumOf { getEncodedStringLength(it) - getStringCodeLength(it) }
}

fun getStringCodeLength(string: String): Int {
    return string.length + 2
}

fun getStringMemoryLength(string: String): Int {
    return string.replace("\\\\\"".toRegex(), "1")
        .replace("\\\\\\\\".toRegex(), "1")
        .replace("\\\\x[0-9a-f]{2}".toRegex(), "1")
        .length
}

fun getEncodedStringLength(string: String): Int {
    return string
        .replace("\\\\\"".toRegex(), "1234")
        .replace("\\\\\\\\".toRegex(), "1234")
        .replace("\\\\x[0-9a-f]{2}".toRegex(), "12345")
        .length + 6
}
