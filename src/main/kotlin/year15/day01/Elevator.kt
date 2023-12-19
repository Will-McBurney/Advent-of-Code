package year15.day01

fun main() {
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val text = reader.readText();
    val startTime = System.currentTimeMillis();
    val result = getFloor(text);
    val endTime = System.currentTimeMillis();
    print("Answer: $result - Calculation time - ${endTime - startTime}ms")
}

fun getFloor(input: String): Int? {
    var floor = 0;
    val charArray = input.toCharArray()
    for (index in charArray.indices) {
        when(charArray[index]) {
            '(' -> floor++
            ')' -> floor--
        }
        if (floor < 0) {
            return index + 1;
        }
    }
    return null //never reached basement
}