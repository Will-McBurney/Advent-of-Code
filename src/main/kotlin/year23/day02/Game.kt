package year23.day02

class Game(
    val id: Int,
    private val draws: List<Draw>
) {
    private val maximumColors : MutableMap<String, Int> = mutableMapOf()

    constructor(line: String): this(
        line.substringBefore(":")
            .substringAfter(" ")
            .toInt(),
        line.substringAfter(":")
            .split(";")
            .map{ string -> Draw(string) }
    )

    init {
        draws.forEach { draw ->
            draw.map.forEach { (key, value) ->
                this.add(key, value)
            }
        }
    }

    fun get(color: String): Int = maximumColors.getOrDefault(color, 0)

    fun add(color: String, minimum: Int) {
        maximumColors[color] = minimum.coerceAtLeast(get(color))
    }

    fun isPossible(bag: Map<String, Int>): Boolean {
        return bag.all { entry ->
            entry.value >= get(entry.key)
        }
    }

    val power
        get() = maximumColors.values.reduce(Int::times)

    override fun toString(): String {
        return "Game(id=$id, draws=$draws, maximumColors=$maximumColors)"
    }
}