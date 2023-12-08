package year15.day14

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ReindeerTest {

    private lateinit var reindeer: Reindeer

    @BeforeEach
    fun setUp() {
        reindeer = Reindeer("Randolph",14, 10, 127)
    }

    @Test
    fun getCycleTimeSecondsTest() {
        assertEquals(137, reindeer.cycleTimeSeconds)
    }

    @Test
    fun getCycleTimeDistanceTest() {
        assertEquals(140, reindeer.cycleDistanceKms)
    }

    @Test
    fun getDistanceTraveledTest() {
        assertEquals(1120, reindeer.getDistanceTraveled(1000))
    }

    @Test
    fun incrementPointsTest() {
        reindeer.incrementPoints()
        assertEquals(1, reindeer.points)
    }
}