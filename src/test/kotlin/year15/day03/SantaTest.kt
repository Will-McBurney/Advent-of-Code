package year15.day03

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class SantaTest {
    @Test
    fun move_up() {
        val santa = Santa()
        santa.move('^')
        assertEquals(Coordinate(0, 1), santa.getPosition())
    }

    @Test
    fun move_left() {
        val santa = Santa()
        santa.move('<')
        assertEquals(Coordinate(-1, 0), santa.getPosition())
    }

    @Test
    fun move_down() {
        val santa = Santa()
        santa.move('v')
        assertEquals(Coordinate(0, -1), santa.getPosition())
    }

    @Test
    fun move_right() {
        val santa = Santa()
        santa.move('>')
        assertEquals(Coordinate(1, 0), santa.getPosition())
    }

    @Test
    fun getHousesVisited() {
        val santa = Santa() // start houses visited - 1
        santa.move('>') // new house - 2
        santa.move('>') // new house - 3
        santa.move('>') // new house - 4
        santa.move('<') // already visited
        santa.move('<') // already visited
        santa.move('v') // new house - 5
        santa.move('^') // already visited
        assertEquals(5, santa.getHousesVisited());
    }
}