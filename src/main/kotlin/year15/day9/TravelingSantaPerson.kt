package year15.day9

fun main() {
    val startTime = System.currentTimeMillis()
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val distanceMap = getDistanceMap(lines)
    val locations = getLocations(distanceMap)
    println(locations)
    println(distanceMap)
    val result1 = getPart1Result(locations, distanceMap)
    val result2 = getPart2Result(locations, distanceMap)
    val endTime = System.currentTimeMillis()
    print("""
        Part1: $result1
        Part2: $result2 
        Calculation time - ${endTime - startTime}ms"""
        .trimIndent())
}



fun getPart1Result(locations: Set<String>, distanceMap: Map<Pair<String, String>, Int>): Int {
    val permutations = getPermutations(locations)
    return permutations.minOf{ getDistance(it, distanceMap) }
}

fun getPart2Result(locations: Set<String>, distanceMap: Map<Pair<String, String>, Int>): Int {
    val permutations = getPermutations(locations)
    return permutations.maxOf{ getDistance(it, distanceMap) }
}

fun getDistance(path: MutableList<String>, distanceMap: Map<Pair<String, String>, Int>): Int {
    var sum = 0
    for (index in 0..< path.size - 1) {
        sum += distanceMap[Pair(path[index], path[index + 1])]!!
    }
    return sum
}

fun getPermutations(locations: Set<String>): MutableList<MutableList<String>> {
    if (locations.isEmpty()) {
        return mutableListOf(mutableListOf())
    }

    val paths = mutableListOf<MutableList<String>>()
    for (location in locations) {
        val listCopy = locations.toMutableSet()
        listCopy.remove(location)
        val innerPaths = getPermutations(listCopy)
        innerPaths.forEach { it.add(0, location) }
        innerPaths.forEach { paths.add(it) }
    }
    return paths;
}

fun getLocations(distanceMap: Map<Pair<String, String>, Int>): Set<String> {
    return distanceMap.keys
        .map(Pair<String, String>::first)
        .toSet()
}

fun getDistanceMap(lines: List<String>): Map<Pair<String, String>, Int> {
    val distanceMap = hashMapOf<Pair<String, String>, Int>()
    for (line in lines) {
        if (line.isEmpty()) continue
        val triple = lineToTriplet(line)
        distanceMap[Pair(triple.first, triple.second)] = triple.third
        distanceMap[Pair(triple.second, triple.first)] = triple.third
    }
    return distanceMap
}



val pattern = "([A-Za-z]+) to ([A-Za-z]+) = ([0-9]+)".toRegex()

fun lineToTriplet(line: String): Triple<String, String, Int> {
    val matchGroups = pattern.find(line)!!.groupValues
    return Triple(matchGroups[1], matchGroups[2], matchGroups[3].toInt())
}