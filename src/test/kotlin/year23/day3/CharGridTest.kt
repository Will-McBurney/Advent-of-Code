package year23.day3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class CharGridTest {
    private lateinit var grid: CharGrid

    @BeforeEach
    fun setup() {
        val input = """
            1234
            5678
            90ab
            """.trimIndent().split("\n")
        grid = CharGrid(input)
    }

    @Test
    fun secondaryConstructor() {
        assertEquals(3, grid.height)
        assertEquals(4, grid.width)
    }

    @Test
    fun getNeighbors_middle() {
        val neighbors = grid.getNeighborsCoordinates(1, 1)
        assertEquals(8, neighbors.size)
        //above
        assertTrue(neighbors.contains(Pair(0, 0)))
        assertTrue(neighbors.contains(Pair(0, 1)))
        assertTrue(neighbors.contains(Pair(0, 2)))

        //same row
        assertTrue(neighbors.contains(Pair(1, 0)))

        assertTrue(neighbors.contains(Pair(1, 2)))

        //below
        assertTrue(neighbors.contains(Pair(2, 0)))
        assertTrue(neighbors.contains(Pair(2, 1)))
        assertTrue(neighbors.contains(Pair(2, 2)))
    }

    @Test
    fun getNeighbors_topLeft_corner() {
        val neighbors = grid.getNeighborsCoordinates(0, 0)
        assertEquals(3, neighbors.size)
        assertTrue(neighbors.contains(Pair(0, 1)))
        assertTrue(neighbors.contains(Pair(1, 0)))
        assertTrue(neighbors.contains(Pair(1, 1)))
    }

    @Test
    fun getNeighbors_bottomRight_corner() {
        val neighbors = grid.getNeighborsCoordinates(2, 3)
        assertEquals(3, neighbors.size)
        assertTrue(neighbors.contains(Pair(1, 2)))
        assertTrue(neighbors.contains(Pair(1, 3)))
        assertTrue(neighbors.contains(Pair(2, 2)))
    }

    @Test
    fun getNeighbors_side() {
        val neighbors = grid.getNeighborsCoordinates(0, 2)
        assertEquals(5, neighbors.size)
        assertTrue(neighbors.contains(Pair(0, 1)))
        assertTrue(neighbors.contains(Pair(0, 3)))
        assertTrue(neighbors.contains(Pair(1, 1)))
        assertTrue(neighbors.contains(Pair(1, 2)))
        assertTrue(neighbors.contains(Pair(1, 3)))
    }

    @Test
    fun specNumber_neighbors() {
        val specNumber = SpecNumber(1, 1, 2, 67)
        specNumber.getNeighbors(grid).forEach(::println)
    }
}