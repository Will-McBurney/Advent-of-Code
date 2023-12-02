package year15.day6

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import year15.day2.getRibbon

class LightGridTest {

    @Test
    fun initOff() {
        val grid = LightGrid(10)
        for (i in 0..9) {
            for (j in 0..9) {
                assertFalse(grid.get(i, j))
            }
        }
    }

    @Test
    fun turnOn() {
        val grid = LightGrid(2)
        grid.turnOn(1, 0)
        assertTrue(grid.get(1, 0))
    }

    @Test
    fun turnOn_alreadyOn() {
        val grid = LightGrid(2)
        grid.turnOn(1, 0)
        grid.turnOn(1, 0)
        assertTrue(grid.get(1, 0))
    }

    @Test
    fun turnOff_alreadyOff() {
        val grid = LightGrid(2)
        // on then off
        grid.turnOff(1, 0)
        assertFalse(grid.get(1, 0))
    }

    @Test
    fun turnOff_onThenOff() {
        val grid = LightGrid(2)
        // on then off
        grid.turnOn(1, 0)
        grid.turnOff(1, 0)
        assertFalse(grid.get(1, 0))
    }

    @Test
    fun toggle_initOff() {
        val grid = LightGrid(2)
        grid.turnOff(1, 0)
        grid.toggle(1, 0)
        assertTrue(grid.get(1, 0))
    }

    @Test
    fun toggle_initOn() {
        val grid = LightGrid(2)
        grid.turnOn(1, 0)
        grid.toggle(1, 0)
        assertFalse(grid.get(1, 0))
    }

    @Test
    fun runOperation() {
        val grid = LightGrid(3)
        grid.runOperation(
            Coordinate(0, 0),
            Coordinate(1, 2),
            grid::turnOn
        )
        assertTrue(grid.get(0, 0))
        assertTrue(grid.get(1, 1))
        assertTrue(grid.get(1, 2))

        assertFalse(grid.get(2, 0))
        assertFalse(grid.get(2, 2))
    }

    @Test
    fun acceptCommand_turnOn() {
        val grid = LightGrid(3)
        grid.acceptCommand("turn on 0,0 through 1,2")
        assertTrue(grid.get(0, 0))
        assertTrue(grid.get(1, 1))
        assertTrue(grid.get(1, 2))

        assertFalse(grid.get(2, 0))
        assertFalse(grid.get(2, 2))
    }

    @Test
    fun acceptCommand_toggle() {
        val grid = LightGrid(3)
        grid.acceptCommand("toggle 0,0 through 1,2")
        assertTrue(grid.get(0, 0))
        assertTrue(grid.get(1, 1))
        assertTrue(grid.get(1, 2))

        assertFalse(grid.get(2, 0))
        assertFalse(grid.get(2, 2))
    }

    @Test
    fun getLightsOnCount_init() {
        val grid = LightGrid(3)
        assertEquals(0, grid.getLightsOnCount())
    }

    @Test
    fun getLightsOnCount_afterOperation() {
        val grid = LightGrid(3)
        grid.runOperation(
            Coordinate(1, 1),
            Coordinate(2, 2),
            grid::turnOn
        )
        assertEquals(4, grid.getLightsOnCount())
    }
}