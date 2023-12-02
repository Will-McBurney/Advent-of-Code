package year15.day6

const val DEFAULT_SIZE = 1000


class LightGrid(
    private val size: Int = DEFAULT_SIZE
) {

    private val grid: Array<Array<Boolean>> = Array(size) { Array(size) { false } }


    fun turnOn(x: Int, y: Int) {
        grid[x][y] = true
    }

    fun turnOff(x: Int, y: Int) {
        grid[x][y] = false
    }

    fun toggle(x: Int, y: Int) {
        grid[x][y] = !grid[x][y]
    }

    fun get(x: Int, y: Int): Boolean {
        return grid[x][y]
    }

    fun runOperation(bottomLeft: Coordinate, topRight: Coordinate, operation: (x: Int, y: Int) -> Unit) {
        for (x in bottomLeft.x..topRight.x) {
            for (y in bottomLeft.y..topRight.y) {
                operation(x, y)
            }
        }
    }

    fun acceptCommand(command: String) {
        var operation: ((x: Int, y: Int) -> Unit)? = null
        val commandSplit = command.split(" ")
        var bottomLeftIndex = -1
        if (commandSplit[0] == "toggle") {
            operation = ::toggle
            bottomLeftIndex = 1
        } else if (commandSplit[0] == "turn") {
            operation = when (commandSplit[1]) {
                "on" -> ::turnOn
                "off" -> ::turnOff
                else -> throw IllegalArgumentException("Bad command: $command")
            }
            bottomLeftIndex = 2
        } else {
            throw IllegalArgumentException("Bad command: $command")
        }
        val topRightIndex = bottomLeftIndex + 2
        val bottomLeftSplit = commandSplit[bottomLeftIndex].split(",")
        val topRightSplit = commandSplit[topRightIndex].split(",")
        val bottomLeft = Coordinate(bottomLeftSplit[0].toInt(), bottomLeftSplit[1].toInt())
        val topRight = Coordinate(topRightSplit[0].toInt(), topRightSplit[1].toInt())
        runOperation(bottomLeft, topRight, operation)
    }

    fun getLightsOnCount(): Int {
        return grid.flatten()
            .count{ it }
    }
}

