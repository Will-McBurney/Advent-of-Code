package year15.day06

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class LightGridTest {

    @Test
    fun initOff() {
        val grid = LightGrid(10)
        for (i in 0..9) {
            for (j in 0..9) {
                assertEquals(0, grid.get(i, j))
            }
        }
    }

    @Test
    fun turnOn() {
        val grid = LightGrid(2)
        grid.turnOn(1, 0)
        assertEquals(1, grid.get(1, 0))
    }

    @Test
    fun turnOn_alreadyOn() {
        val grid = LightGrid(2)
        grid.turnOn(1, 0)
        grid.turnOn(1, 0)
        assertEquals(2, grid.get(1, 0))
    }

    @Test
    fun turnOff_alreadyOff() {
        val grid = LightGrid(2)
        grid.turnOff(1, 0)
        assertEquals(0, grid.get(1, 0))
    }

    @Test
    fun turnOff_onThenOff() {
        val grid = LightGrid(2)
        grid.turnOn(1, 0)
        grid.turnOff(1, 0)
        assertEquals(0, grid.get(1, 0))
    }

    @Test
    fun toggle_initOff() {
        val grid = LightGrid(2)
        grid.turnOff(1, 0)
        grid.toggle(1, 0)
        assertEquals(2, grid.get(1, 0))
    }

    @Test
    fun toggle_initOn() {
        val grid = LightGrid(2)
        grid.turnOn(1, 0)
        grid.toggle(1, 0)
        assertEquals(3, grid.get(1, 0))
    }

    @Test
    fun runOperation() {
        val grid = LightGrid(3)
        grid.runOperation(
            Coordinate(0, 0),
            Coordinate(1, 2),
            grid::turnOn
        )
        assertEquals(1, grid.get(0, 0))
        assertEquals(1, grid.get(1, 1))
        assertEquals(1, grid.get(1, 2))

        assertEquals(0, grid.get(2, 0))
        assertEquals(0, grid.get(2, 2))
    }

    @Test
    fun acceptCommand_turnOn() {
        val grid = LightGrid(3)
        grid.acceptCommand("turn on 0,0 through 1,2")
        assertEquals(1, grid.get(0, 0))
        assertEquals(1, grid.get(1, 1))
        assertEquals(1, grid.get(1, 2))

        assertEquals(0, grid.get(2, 0))
        assertEquals(0, grid.get(2, 2))
    }

    @Test
    fun acceptCommand_toggle() {
        val grid = LightGrid(3)
        grid.acceptCommand("toggle 0,0 through 1,2")
        assertEquals(2, grid.get(0, 0))
        assertEquals(2, grid.get(1, 1))
        assertEquals(2, grid.get(1, 2))

        assertEquals(0, grid.get(2, 0))
        assertEquals(0, grid.get(2, 2))
    }

    @Test
    fun getLightsOnCount_init() {
        val grid = LightGrid(3)
        assertEquals(0, grid.getTotalBrightness())
    }

    @Test
    fun getLightsOnCount_afterOperation() {
        val grid = LightGrid(3)
        grid.runOperation(
            Coordinate(1, 1),
            Coordinate(2, 2),
            grid::turnOn
        )
        assertEquals(4, grid.getTotalBrightness())
    }
}