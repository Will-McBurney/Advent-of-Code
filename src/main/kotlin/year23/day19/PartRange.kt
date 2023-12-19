package year23.day19

class PartRange(
    val xRange: IntRange,
    val mRange: IntRange,
    val aRange: IntRange,
    val sRange: IntRange
) {
    constructor(rangeLow: Int, rangeHigh:Int):
            this((rangeLow .. rangeHigh),
                (rangeLow .. rangeHigh),
                (rangeLow .. rangeHigh),
                (rangeLow .. rangeHigh))


    companion object {
        fun getEmptyPartRange(): PartRange {
            return PartRange((-1..-1),(-1..-1),(-1..-1),(-1..-1))
        }

        fun isEmptyPartRange(partRange: PartRange): Boolean {
            return partRange.xRange == (-1..-1) &&
                    partRange.mRange == (-1..-1) &&
                    partRange.aRange == (-1..-1) &&
                    partRange.sRange == (-1..-1)
        }
    }

    private fun getRange(letter: Char): IntRange {
        return when(letter) {
            'x' -> xRange
            'm' -> mRange
            'a' -> aRange
            's' -> sRange
            else -> throw IllegalArgumentException("Bad letter: char - $letter")
        }
    }

    fun isSplitNeeded(letter: Char, greaterThan: Boolean, number: Int): Boolean {
        val targetNumber = if (greaterThan) { number } else { number - 1 }
        val range = getRange(letter)
        return range.first <= targetNumber && xRange.last > targetNumber
    }

    fun isFullRangeTrue(letter: Char, greaterThan: Boolean, number: Int): Boolean {
        return if (greaterThan) {
            getRange(letter).first > number
        } else {
            getRange(letter).last < number
        }
    }

    fun isFullRangeFalse(letter: Char, greaterThan: Boolean, number: Int): Boolean {
        return if (greaterThan) {
            getRange(letter).last <= number
        } else {
            getRange(letter).first >= number
        }
    }

    /**
     * Split a PartRange into two PartRanges. The "lower" value comes first, the "higher" value comes second
     */
    fun splitRange(letter: Char, greaterThan: Boolean, number: Int): Pair<PartRange, PartRange> {
        val maxLowerRange = if (greaterThan) { number } else { number - 1 }
        val xRange = this.xRange
        val mRange = this.mRange
        val aRange = this.aRange
        val sRange = this.sRange
        when(letter) {
            'x' -> {
                val lowerRange = xRange.first .. maxLowerRange
                val higherRange = maxLowerRange + 1 .. xRange.last
                return Pair(
                    PartRange(lowerRange, mRange, aRange, sRange),
                    PartRange(higherRange, mRange, aRange, sRange)
                )
            }
            'm' -> {
                val lowerRange = mRange.first .. maxLowerRange
                val higherRange = maxLowerRange + 1 .. mRange.last
                return Pair(
                    PartRange(xRange, lowerRange, aRange, sRange),
                    PartRange(xRange, higherRange, aRange, sRange)
                )
            }
            'a' -> {
                val lowerRange = aRange.first .. maxLowerRange
                val higherRange = maxLowerRange + 1 .. aRange.last
                return Pair(
                    PartRange(xRange, mRange, lowerRange, sRange),
                    PartRange(xRange, mRange, higherRange, sRange)
                )
            }
            's' -> {
                val lowerRange = sRange.first .. maxLowerRange
                val higherRange = maxLowerRange + 1 .. sRange.last
                return Pair(
                    PartRange(xRange, mRange, aRange, lowerRange),
                    PartRange(xRange, mRange, aRange, higherRange)
                )
            }
        }
        throw IllegalArgumentException("Invalid letter: $letter")
    }

    fun getPartPossibilities(): Long {
        return "xmas".map { letter -> getRangeSize(letter).toLong() }.reduce(Long::times)
    }

    fun getRangeSize(letter: Char): Int {
        val range = getRange(letter)
        return range.last - range.first + 1
    }
}