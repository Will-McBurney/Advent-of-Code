package year23.day10

class Loop(
    val startingX: Int,
    val startingY: Int
) {
    val path: MutableList<Pair<Int, Int>> = mutableListOf()

    fun add(coordinates: Pair<Int, Int>) {
        path.add(coordinates)
    }

    fun get(index: Int) {
        return
    }

    fun contains(coordinates: Pair<Int, Int>): Boolean {
        return path.contains(coordinates)
    }

    fun getFarthestFromStart(): Pair<Int, Int> {
        return path[path.size / 2]
    }

    fun getSize(): Int {
        return path.size
    }

    override fun toString(): String {
        return path.toString()
    }
}