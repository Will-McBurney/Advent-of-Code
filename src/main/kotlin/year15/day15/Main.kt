package year15.day15

fun main() {
    val startTime = System.currentTimeMillis()
    val maxTablespoons = 100
    val targetCalories = 500

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val ingredients = getIngredients(lines)
    val permutations = getPermutations(ingredients.size, maxTablespoons)
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(permutations, ingredients, maxTablespoons)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result(permutations, ingredients, maxTablespoons, targetCalories)
    val part2EndTime = System.currentTimeMillis()

    //Display output
    println(
        """
        |Read Time: %10d ms
        |
        |Part One:  %10d - Time %6d ms
        |Part Two:  %10d - Time %6d ms
        |
        |Total time - ${part2EndTime - startTime}ms
        |""".trimMargin().format(readEndTime - startTime,
            part1Result,
            part1EndTime - readEndTime,
            part2Result,
            part2EndTime - part1EndTime)
    )
}

val LINE_PATTERN = "([A-Za-z])+: capacity (-?[0-9]), durability (-?[0-9]), flavor (-?[0-9]), texture (-?[0-9]), calories (-?[0-9])".toRegex()

private const val i1 = 4

fun getIngredients(lines: List<String>): List<Ingredient> {
    return lines.map{ it.trim() }
        .filterNot { it.isEmpty() }
        .map{ LINE_PATTERN.find(it)!!.groups}
        .map{ Ingredient(it[1]!!.value,
            intArrayOf(it[2]!!.value.toInt(), it[3]!!.value.toInt(), it[4]!!.value.toInt(), it[5]!!.value.toInt()),
            it[6]!!.value.toInt())
        }
        .toList()
}


fun getPart1Result(permutations: List<List<Int>>, ingredients: List<Ingredient>, maxTablespoons: Int): Int {
    return getBestPermutationScore(permutations, ingredients)
}

private fun getBestPermutationScore(permutations: List<List<Int>>, ingredients: List<Ingredient>): Int {
    return permutations.asSequence()
        .map {getWeightedSum(it, ingredients) }
        .map {it.reduce(Int::times)}
        .max()
}

private fun getWeightedSum(
    permutation: List<Int>,
    ingredients: List<Ingredient>
): MutableList<Int> {
    var weightedSum = mutableListOf(0, 0, 0, 0)
    for (ingredientIndex in permutation.indices) {
        val count = permutation[ingredientIndex]
        for (i in 0..<4) {
            weightedSum[i] += ingredients[ingredientIndex].scores[i] * count
        }
    }
    return weightedSum.map { if (it < 0) 0 else it }.toMutableList()
}

fun getPermutations(numberOfIngredients: Int, maxTablespoons: Int): List<List<Int>> {
   return getPermutationsHelper(numberOfIngredients, maxTablespoons)
}

fun getPermutationsHelper(columnsRemaining: Int, remainingTableSpoons: Int): MutableList<MutableList<Int>> {
    if (columnsRemaining == 1) {
        return mutableListOf(mutableListOf(remainingTableSpoons))
    }
    val outerPermutations = mutableListOf<MutableList<Int>>()
    (0 .. remainingTableSpoons).forEach {ingredientTablespoons ->
        val permutations = getPermutationsHelper(columnsRemaining - 1,
            remainingTableSpoons - ingredientTablespoons
        )
        permutations.forEach {
            it.add(ingredientTablespoons)
        }
        outerPermutations.addAll(permutations)
    }
    return outerPermutations
}

fun getPart2Result(permutations: List<List<Int>>, ingredients: List<Ingredient>,
                   maxTablespoons: Int, targetCalories: Int): Int {
    return getBestPermutationScore(permutations
        .filter { getTotalCalories(it, ingredients) == targetCalories }
        .toList(),
        ingredients)
}

fun getTotalCalories(ingredientCounts: List<Int>, ingredients: List<Ingredient>): Int {
    return ingredientCounts.mapIndexed {index, count ->
        ingredients[index].calories * count
    }.sum()
}
