package year15.day15

data class Ingredient(
    val name: String,
    val scores: IntArray,
    val calories: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ingredient

        if (name != other.name) return false
        if (!scores.contentEquals(other.scores)) return false
        if (calories != other.calories) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + scores.contentHashCode()
        result = 31 * result + calories
        return result
    }

}