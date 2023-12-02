package year23.day2

import java.io.BufferedReader



fun main() {
    val startTime = System.currentTimeMillis();
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val result = getResult(reader)
    val endTime = System.currentTimeMillis();
    println("""Possible Games: ${result.first} 
        |Total Power: ${result.second}
        |Calculation time - ${endTime - startTime}ms
        |""".trimMargin())
}

fun getResult(reader: BufferedReader): Pair<Int, Int> {
    var possibleSum = 0;
    var powerSum = 0
    for (line in reader.lineSequence()) {
        val idMatch = """Game ([0-9]+):""".toRegex().find(line)!!
        val game = Game(idMatch.groupValues[1].toInt());
        val roundsString = line.substring(line.indexOf(":")+1).trim()
        val roundsSplit = roundsString.split(";")
        for (round in roundsSplit) {
            val redMatch = """([0-9]+) red""".toRegex().find(round)
            if (redMatch != null) game.updateRed(redMatch.groupValues[1].toInt())

            val blueMatch = """([0-9]+) blue""".toRegex().find(round)
            if (blueMatch != null) game.updateBlue(blueMatch.groupValues[1].toInt())

            val greenMatch = """([0-9]+) green""".toRegex().find(round)
            if (greenMatch != null) game.updateGreen(greenMatch.groupValues[1].toInt())
        }
        if (game.isPossible()) {
            possibleSum += game.id
        }
        powerSum+= game.knownRed * game.knownBlue * game.knownGreen
    }
    return Pair(possibleSum, powerSum);
}