package year15.day9

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class TravelingSantaPersonKtTest {

    @Test
    fun lineToTriplet() {
        val triple = lineToTriplet("Tristram to Tambi = 63")
        assertEquals("Tristram", triple.first)
        assertEquals("Tambi", triple.second)
        assertEquals(63, triple.third)
    }

    @Test
    fun testDistance() {
        val input = mutableListOf("Tristram", "AlphaCentauri", "Snowdin", "Tambi",
            "Faerun", "Norrath", "Straylight")
        val distanceMap = mapOf(
            Pair("Tristram", "AlphaCentauri") to 1,
            Pair("AlphaCentauri", "Snowdin") to 2,
            Pair("Snowdin", "Tambi") to 3,
            Pair("Tambi", "Faerun") to 4,
            Pair("Faerun", "Norrath") to 5,
            Pair("Norrath", "Straylight") to 6,
        )
        assertEquals(21, getDistance(input, distanceMap))
    }
}