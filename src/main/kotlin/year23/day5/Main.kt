package year23.day5

import java.io.BufferedReader

fun main() {
    val startTime = System.currentTimeMillis()
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val seeds = getSeeds(reader)
    val mapperChain = getMapperChain(reader)
    val getPart1Result = getPart1Result(seeds, mapperChain)
    val getPart2Result = getPart2Result(seeds, mapperChain)
    val endTime = System.currentTimeMillis()
    println(
        """
        |Part One: $getPart1Result 
        |Part Two: $getPart2Result
        |Calculation time - ${endTime - startTime}ms
        |""".trimMargin()
    )
}


fun getSeeds(reader: BufferedReader): List<Long> {
    val seedLine = reader.readLine()!!

    val seedsString = seedLine.substringAfter("seeds: ")
    val seeds = seedsString.split(" ")
        .map(String::toLong)
        .toList()

    reader.readLine() //skip blank line
    return seeds
}

fun getMapperChain(reader: BufferedReader): MapperChain {
    val mapperList = mutableListOf<Mapper>()
    var tripleList = mutableListOf<Triple<Long, Long, Long>>()
    for (line in reader.readLines()) {
        if (line.trim().isEmpty()) {
            mapperList.add(Mapper(tripleList))
            tripleList = mutableListOf<Triple<Long, Long, Long>>()
            continue
        }
        if (line.matches("^[A-Za-z].*$".toRegex())) {
            continue
        }
        val numbers = line.trim()
            .split(" ")
            .map(String::toLong)
        tripleList.add(Triple(numbers[0], numbers[1], numbers[2]))
    }
    if (tripleList.isNotEmpty()) {
        mapperList.add(Mapper(tripleList))
    }
    return MapperChain(mapperList)
}

fun getPart1Result(seeds: List<Long>, mapperChain: MapperChain): Long =
    seeds.minOf { mapperChain.getData(it, MapperChain.Datum.LOCATION) }

fun getPart2Result(seeds: List<Long>, mapperChain: MapperChain): Long {
    val seedRanges = getSeedRanges(seeds)
    var i = 0L
    while (true) {
        val minOpt = (i ..< i+100000).toList().parallelStream()
            .map { mapperChain.getSeedFromLocation(it) }
            .filter { seed -> seedRanges.any { it.contains(seed)} }
            .mapToLong{ mapperChain.getData(it, MapperChain.Datum.LOCATION) }
            .min()
        if (minOpt.isPresent) return minOpt.asLong
        i+=100000
    }
}


fun getSeedRanges(seeds: List<Long>): List<LongRange> {
    val rangeList = mutableListOf<LongRange>()
    for (index in seeds.indices step 2) {
        rangeList.add(seeds[index]..<seeds[index] + seeds[index + 1])
    }
    return rangeList
}

