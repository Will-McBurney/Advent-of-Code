package year15.day06

fun main() {
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val text = reader.readText();
    val startTime = System.currentTimeMillis();
    val result = getNumberOfLightsOn(text)
    val endTime = System.currentTimeMillis();
    print("Answer: $result - Calculation time - ${endTime - startTime}ms")
}

fun getNumberOfLightsOn(text: String): Any {
    val grid = LightGrid()
    text.split("\n").stream()
        .filter{ it.isNotEmpty() }
        .forEach{ grid.acceptCommand(it) }
    return grid.getTotalBrightness();
}
