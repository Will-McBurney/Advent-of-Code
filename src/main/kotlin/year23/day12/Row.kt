package year23.day12


class Row(
    val startingState: String,
    val configuration: List<Int>
) {
    val possibilities = getPossibilitiesArray(startingState)
    val required = getRequiredArray(startingState)

    private fun getPossibilitiesArray(startingState: String): IntArray {
        val possibilitiesArray = IntArray(startingState.length) { 0 }
        for (startingIndex in possibilitiesArray.indices) {
            if ('.' == startingState[startingIndex]) continue
            possibilitiesArray[startingIndex]++
            var searchingIndex = startingIndex + 1
            while (searchingIndex < startingState.length && startingState[searchingIndex] != '.') {
                possibilitiesArray[startingIndex]++
                searchingIndex++
            }
        }
        return possibilitiesArray
    }

    private fun getRequiredArray(startingState: String): IntArray {
        val requiredArray = IntArray(startingState.length) { 0 }
        for (startingIndex in requiredArray.indices) {
            if ('.' == startingState[startingIndex] || '?' == startingState[startingIndex]) continue
            requiredArray[startingIndex]++
            var searchingIndex = startingIndex + 1
            while (searchingIndex < startingState.length && startingState[searchingIndex] == '#') {
                requiredArray[startingIndex]++
                searchingIndex++
            }
        }
        return requiredArray
    }

    fun getArrangementsByBruteForce(): Long {
        val permutations = getPermutations()
        return permutations.count { isPossible(it) }.toLong()
    }

    fun isPossible(candidate: String): Boolean {
        if (candidate.length != startingState.length) return false
        return candidate.indices.all{isCharacterPossible(candidate[it], startingState[it])}
    }

    fun isCharacterPossible(charCandidate: Char, charState: Char): Boolean {
        if (charState == '?') return true
        if (charCandidate == charState) return true
        return false
    }

    fun getPermutations(): List<String> {
        return getPermutations(0, configuration)
    }

    fun getPermutations(startingIndex: Int, remainingNumbers: List<Int>): List<String> {
        val outerList: MutableList<String> = mutableListOf()
        if (startingIndex + getMinimumRemainingLength(remainingNumbers) > startingState.length) {
            return outerList
        }

        if (remainingNumbers.isEmpty()) {
            return listOf(".".repeat(startingState.length - startingIndex.coerceAtMost(startingState.length)))
        }
        if (startingIndex + remainingNumbers[0] > startingState.length - 1) {
            return listOf("#".repeat(remainingNumbers[0]))
        }
        val recurseTrue = getPermutations(startingIndex + remainingNumbers[0] + 1, remainingNumbers.subList(1, remainingNumbers.size))
        val broken = "#".repeat(remainingNumbers[0])
        recurseTrue
            .map{ "$broken.$it" }
            .forEach { outerList.add(it) }
        getPermutations(startingIndex + 1, remainingNumbers)
            .map{ ".$it" }
            .forEach { outerList.add(it) }
        return outerList
    }

    fun getArrangementsCount(): Long {
        val output = getArrangementsCount(0, configuration, mutableMapOf<Pair<Int, List<Int>>, Long>());
        return output
    }

    fun getArrangementsCount(startingIndex: Int, remainingNumbers: List<Int>, cache: MutableMap<Pair<Int, List<Int>>, Long>): Long {
        if (cache[Pair(startingIndex, remainingNumbers)] != null) {
            return cache[Pair(startingIndex, remainingNumbers)]!!
        }
        if (remainingNumbers.isEmpty() && (
                startingIndex >= startingState.length ||
                startingState.substring(startingIndex).none { it == '#' })) {
            //println("returning - ${startingIndex.toString(16)})")
            cache[Pair(startingIndex, remainingNumbers)] = 1L
            return 1L
        }
        if (remainingNumbers.isEmpty()) {
            cache[Pair(startingIndex, remainingNumbers)] = 0
            return 0
        }
        if (startingIndex >= startingState.length) {
            cache[Pair(startingIndex, remainingNumbers)] = 0
            return 0
        }
        if (startingIndex + getMinimumRemainingLength(remainingNumbers) > startingState.length) {
            cache[Pair(startingIndex, remainingNumbers)] = 0
            return 0
        }
        if (startingState[startingIndex] == '.') {
            val out = getArrangementsCount(startingIndex + 1, remainingNumbers, cache)
            cache[Pair(startingIndex, remainingNumbers)] = out
            return out
        }
        if (required[startingIndex] > 0 && possibilities[startingIndex]  < remainingNumbers[0]) {
            cache[Pair(startingIndex, remainingNumbers)] = 0
            return 0
        }
        if (possibilities[startingIndex] < remainingNumbers[0]) {
            val out = getArrangementsCount(startingIndex + 1, remainingNumbers, cache)
            cache[Pair(startingIndex, remainingNumbers)] = out
            return out
        }

        if (required[startingIndex] > 0 && possibilities[startingIndex] >= remainingNumbers[0] &&
            (remainingNumbers[0] + startingIndex == startingState.length ||
                    required[remainingNumbers[0] + startingIndex] == 0)) {
            val out = getArrangementsCount(
                startingIndex + remainingNumbers[0] + 1,
                remainingNumbers.subList(1, remainingNumbers.size), cache
            )
            cache[Pair(startingIndex, remainingNumbers)] = out
            return out
        }
        if (required[startingIndex] > 0 && possibilities[startingIndex] >= remainingNumbers[0] &&
            (remainingNumbers[0] + startingIndex == startingState.length ||
                    required[remainingNumbers[0] + startingIndex] > 0)) {
            cache[Pair(startingIndex, remainingNumbers)] = 0
            return 0
        }

        if (startingIndex + remainingNumbers[0] < startingState.length &&
            required[startingIndex + remainingNumbers[0] ] > 0) {
            val out = getArrangementsCount(startingIndex + 1, remainingNumbers, cache)
            cache[Pair(startingIndex, remainingNumbers)] = out
            return out
        }
        val out = getArrangementsCount(
            startingIndex + remainingNumbers[0] + 1,
            remainingNumbers.subList(1, remainingNumbers.size), cache) +
                getArrangementsCount(startingIndex + 1, remainingNumbers, cache)
        cache[Pair(startingIndex, remainingNumbers)] = out
        return out
    }

    private fun getMinimumRemainingLength(remainingNumbers: List<Int>) =
        remainingNumbers.sum() + remainingNumbers.size - 1


    override fun toString(): String {
        return "Row(startingStage='$startingState', configuration=$configuration)"
    }
}