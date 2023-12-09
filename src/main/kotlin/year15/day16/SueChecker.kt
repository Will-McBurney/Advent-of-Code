package year15.day16

interface SueChecker {
    fun isPossible(sue: Sue, knownStuff: Map<String, Int>): Boolean
}

class Part1SueChecker : SueChecker {
    override fun isPossible(sue: Sue, knownStuff: Map<String, Int>): Boolean {
        return knownStuff.keys.all { category ->
            sue.getStuffCount(category) == null || sue.getStuffCount(category) == knownStuff[category]
        }
    }
}

class Part2SueChecker : SueChecker {
    override fun isPossible(sue: Sue, knownStuff: Map<String, Int>): Boolean {
        return knownStuff.keys.all { category ->
            sue.getStuffCount(category) == null ||
                    (when (category) {
                        "cats", "trees" -> sue.getStuffCount(category)!! > knownStuff[category]!!
                        "pomeranians", "goldfish" -> sue.getStuffCount(category)!! < knownStuff[category]!!
                        else -> sue.getStuffCount(category) == knownStuff[category]
                    })
        }
    }
}