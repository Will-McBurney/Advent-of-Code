package year23.day2

import kotlin.math.max
import kotlin.math.min

class Game(id: Int) {
    var maxRed = 0
    var maxBlue = 0
    var maxGreen = 0

    fun updateRed(redCount: Int) {
        maxRed = max(maxRed, redCount);
    }

    fun updateBlue(blueCount: Int) {
        maxBlue = max(maxBlue, blueCount)
    }

    fun updateGreen(greenCount: Int) {
        maxGreen = max(maxGreen, greenCount)
    }

    override fun toString(): String {
        return "Game(maxRed=$maxRed, maxBlue=$maxBlue, maxGreen=$maxGreen)"
    }


}