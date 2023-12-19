package year15.day02

val part = 2 //Use either 1 or 2 for part 1 or 2

fun main() {
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val text = reader.readText();
    val startTime = System.currentTimeMillis();
    val result = when(part) {
        1 -> getWrappingPaperArea(text)
        2 -> getRibbonFeet(text)
        else -> throw RuntimeException()
    }
    val endTime = System.currentTimeMillis();
    print("Answer: $result - Calculation time - ${endTime - startTime}ms")
}

fun getWrappingPaperArea(text: String): Long {
    return text.split("\n").stream()
        .filter { it.isNotEmpty() }
        .map { getDimensions(it) }
        .mapToLong { getArea(it) }
        .sum()
}

fun getRibbonFeet(text: String): Long {
    return text.split("\n").stream()
        .filter { it.isNotEmpty() }
        .map { getDimensions(it) }
        .mapToLong { getRibbon(it) }
        .sum()
}

fun getDimensions(line: String): List<Long> = line.split("x").map { it.toLong() }

fun getArea(dimensions: List<Long>): Long {
    val h = dimensions[0]
    val w = dimensions[1]
    val l = dimensions[2]
    val sides = listOf(h * w, h * l, w * l)
    return  (sides.sum() * 2) + sides.min()
}

fun getRibbon(dimensions: List<Long>): Long {
    val smallestPerimeter = dimensions.sum() - dimensions.max()
    val volume = dimensions.reduce{acc, next -> acc * next}
    return 2 * smallestPerimeter + volume
}
