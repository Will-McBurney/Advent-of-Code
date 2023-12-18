package year23.day18

import java.awt.Color

class Loop {
    val edgeCoordinates: MutableMap<Coordinate, Color> = mutableMapOf()
    var lastCoordinate: Coordinate = Coordinate(0, 0)

    fun addEdge(direction: Direction, distance: Int, color: Color) {
        (0 ..< distance).forEach {
            edgeCoordinates[lastCoordinate] = color
            lastCoordinate = Coordinate(lastCoordinate.row + direction.dRow, lastCoordinate.col + direction.dCol)
        }
    }
    fun contains(coordinate: Coordinate) =
        edgeCoordinates.contains(coordinate)

    val size: Int
        get() = edgeCoordinates.size

    fun getRowRange(): IntRange =
        (edgeCoordinates.keys.minOf { coordinate -> coordinate.row } .. edgeCoordinates.keys.maxOf { coordinate -> coordinate.row })

    fun getColumnRange(): IntRange =
        (edgeCoordinates.keys.minOf { coordinate -> coordinate.col } .. edgeCoordinates.keys.maxOf { coordinate -> coordinate.col })

    override fun toString(): String {
        return edgeCoordinates.keys.joinToString("\n\t") { key -> "$key - ${edgeCoordinates[key]}" }
    }
}