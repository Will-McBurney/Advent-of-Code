package year23.day5

class Mapper(private val inputTriples: List<Triple<Long, Long, Long>>) {
    fun getSourceRange(input: Triple<Long, Long, Long>): LongRange  {
        val sourceStart = input.second
        val sourceEnd = sourceStart + input.third
        return (sourceStart ..< sourceEnd)
    }
    fun getDestinationRange(input: Triple<Long, Long, Long>): LongRange {
        val destinationStart = input.first
        val destinationEnd = destinationStart + input.third
        return (destinationStart ..< destinationEnd)
    }

    fun isInSourceRange(number: Long, triple: Triple<Long, Long, Long>): Boolean {
        return number in getSourceRange(triple)
    }

    private fun isInDestinationRange(number: Long, triple: Triple<Long, Long, Long>): Boolean {
        return number in getDestinationRange(triple)
    }

    fun getMappingInRange(number: Long, triple: Triple<Long, Long, Long>): Long {
        if (!isInSourceRange(number, triple)) {
            throw IllegalArgumentException(
                "$number not in range for $triple - source range is ${getSourceRange(triple)}")
        }
        val delta = number - getSourceRange(triple).first
        return getDestinationRange(triple).first + delta
    }

    private fun getBreakPoints(): List<Long> {
        return inputTriples.map {getSourceRange(it)}
            .map{ listOf(it.first, it.last + 1) }
            .flatten()
            .distinct()
            .sorted()
            .toList()
    }

    fun getMapping(number: Long): Long {
        for (triple in inputTriples) {
            if (isInSourceRange(number, triple)) {
                return getMappingInRange(number, triple)
            }
        }
        return number
    }

    private fun getReverseMappingInRange(number: Long, triple: Triple<Long, Long, Long>): Long {
        if (!isInDestinationRange(number, triple)) {
            throw IllegalArgumentException(
                "$number not in range for $triple - destination range is ${getDestinationRange(triple)}")
        }
        val delta = number - getDestinationRange(triple).first
        return getSourceRange(triple).first + delta
    }

    fun getReverseMapping(number: Long): Long {
        for (triple in inputTriples) {
            if (isInDestinationRange(number, triple)) {
                return getReverseMappingInRange(number, triple)
            }
        }
        return number
    }

    override fun toString(): String {
        return "Mapper(inputTriples=$inputTriples)"
    }

    fun mapRanges(ranges: List<LongRange>): List<LongRange> {
        val breakPoints = getBreakPoints()
        val sourceRanges = mutableListOf<LongRange>()
        for (range in ranges) {
            var currentRange = range
            for (breakpoint in breakPoints) {
                if (currentRange.contains(breakpoint)) {
                    val splitRanges = splitRange(currentRange, breakpoint)
                    sourceRanges.add(splitRanges[0])
                    currentRange = splitRanges[1]
                }
            }
            sourceRanges.add(currentRange)
        }
        return sourceRanges.map{(getMapping(it.first)..getMapping(it.last))}
            .toList()

    }

    private fun splitRange(range: LongRange, splitPoint: Long): List<LongRange> {
        if (!range.contains(splitPoint)) {
            throw java.lang.IllegalArgumentException("Split point $splitPoint invalid for range $range")
        }
        return listOf(
            (range.first ..< splitPoint),
            (splitPoint ..< range.last)
        )
    }


}

