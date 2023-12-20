package year23.day02

class Draw(
    val map: Map<String, Int> = mutableMapOf()
) {
    constructor(draw: String):
            this(draw.split(",")
                    .map(String::trim)
                    .map{ it.split(" ")}
                    .associate { it[1] to it[0].toInt() }
            )

    override fun toString(): String {
        return "Draw(map=$map)"
    }


}
