package year23.day16

class MirrorGrid(
    private val mirrorGrid: List<List<Char>>,
    private val startingBeam: Beam

    ) {
    private val beams: MutableList<Beam> = mutableListOf(startingBeam)
    private val usedSplitters: MutableSet<Coordinate> = mutableSetOf()

    init {
        beams[0].updateDirection(get(beams[0].getPosition()))
    }

    fun isInBounds(coordinate: Coordinate): Boolean {
        return (coordinate.row in mirrorGrid.indices && coordinate.column in mirrorGrid[0].indices)
    }

    fun get(coordinate: Coordinate): Char {
        return mirrorGrid[coordinate.row][coordinate.column]
    }

    fun advance() {
        beams.forEach { beam ->
            beam.advance()
        }

        beams.filter { beam -> !isInBounds(beam.getPosition()) }
            .forEach { beam -> beam.stopBeam() }

        beams.filter { beam -> isInBounds(beam.getPosition()) }
            .forEach { beam ->
                val newPosition = beam.getPosition()
                val newPositionChar = get(newPosition)
                beam.updateDirection(newPositionChar)
                if (beam.isSplitBeam(newPositionChar) && !usedSplitters.contains(newPosition)) {
                    usedSplitters.add(newPosition)
                    when (newPositionChar) {
                        '|' -> {
                            beam.direction = Direction.UP
                            beams.add(Beam(newPosition, Direction.DOWN))
                        }

                        '-' -> {
                            beam.direction = Direction.LEFT
                            beams.add(Beam(newPosition, Direction.RIGHT))
                        }
                    }
                }
                else if(beam.isSplitBeam(newPositionChar)) {
                    beam.direction = Direction.NONE
                }
            }
    }

    fun hasBeamsRemaining(): Boolean {
        return beams.any { beam -> beam.direction != Direction.NONE }
    }

    fun getEnergizedCoordinates(): List<Coordinate> {
        return beams.map { beam -> beam.getVisited() }
            .flatten()
            .distinct()
            .filter { coordinate -> isInBounds(coordinate) }
    }

    override fun toString(): String {
        return mirrorGrid.joinToString("\n") { it.joinToString("") }
    }

    fun beamsToString(): String {
        val energizedCoordinate = getEnergizedCoordinates().toSet()
        return mirrorGrid.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, _ ->
                if (energizedCoordinate.contains(Coordinate(rowIndex, columnIndex))) {
                    "#"
                } else {
                    "."
                }
            }.joinToString("")
        }.joinToString("\n")
    }
}