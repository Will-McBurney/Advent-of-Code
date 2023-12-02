package year23.day2

import java.io.BufferedReader


fun main() {
    val startTime = System.currentTimeMillis();
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val sum = getResult(reader)
    val endTime = System.currentTimeMillis();
    println("Answer: $sum - Calculation time - ${endTime - startTime}ms")
}

fun getResult(reader: BufferedReader): Int {
    var sum = 0
    for (line in reader.lineSequence()) {
        val gameID = line.split(" ")[1].replace(":", "").toInt()
        val game = Game(gameID);
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
        sum+= game.maxRed * game.maxBlue * game.maxGreen
    }
    return sum;
}