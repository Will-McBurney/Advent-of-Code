package year23.day02

import kotlin.math.max

const val POSSIBLE_RED = 12
const val POSSIBLE_GREEN = 13
const val POSSIBLE_BLUE = 14

class Game(val id: Int) {
    var knownRed = 0
    var knownBlue = 0
    var knownGreen = 0

    fun updateRed(redCount: Int) {
        knownRed = max(knownRed, redCount);
    }

    fun updateBlue(blueCount: Int) {
        knownBlue = max(knownBlue, blueCount)
    }

    fun updateGreen(greenCount: Int) {
        knownGreen = max(knownGreen, greenCount)
    }

    fun isPossible(): Boolean {
        return (knownRed <= POSSIBLE_RED) && (knownBlue <= POSSIBLE_BLUE) && (knownGreen <= POSSIBLE_GREEN)
    }

    fun getPower(): Int {
        return knownRed * knownBlue * knownGreen
    }

    override fun toString(): String {
        return "Game(maxRed=$knownRed, maxBlue=$knownBlue, maxGreen=$knownGreen)"
    }


}