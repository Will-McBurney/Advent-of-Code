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

    fun isSplitNeeded(letter: Char, greaterThan: Boolean, number: Int): Boolean {
        val targetNumber = if (greaterThan) { number } else { number - 1 }
        return when(letter) {
            'x' -> xRange.first <= targetNumber && xRange.last > targetNumber
            'm' -> mRange.first <= targetNumber && mRange.last > targetNumber
            'a' -> aRange.first <= targetNumber && aRange.last > targetNumber
            's' -> sRange.first <= targetNumber && sRange.last > targetNumber
            else -> throw IllegalArgumentException("Bad letter: char - $letter")
        }
    }

    fun isFullRangeTrue(letter: Char, greaterThan: Boolean, number: Int): Boolean {
        val targetRange = when(letter) {
            'x' -> xRange
            'm' -> mRange
            'a' -> aRange
            's' -> sRange
            else -> throw IllegalArgumentException("Bad letter: char - $letter")
        }
        return if (greaterThan) {
            targetRange.first > number
        } else {
            targetRange.last < number
        }
    }

    fun isFullRangeFalse(letter: Char, greaterThan: Boolean, number: Int): Boolean {
        val targetRange = when(letter) {
            'x' -> xRange
            'm' -> mRange
            'a' -> aRange
            's' -> sRange
            else -> throw IllegalArgumentException("Bad letter: char - $letter")
        }
        return if (greaterThan) {
            targetRange.last <= number
        } else {
            targetRange.first >= number
        }
    }

    /**
     * Split a PartRange into two PartRanges. The "lower" value comes first, the "higher" value comes second
     */
    fun splitRange(letter: Char, greaterThan: Boolean, number: Int): Pair<PartRange, PartRange> {
        val maxLowerRange = if (greaterThan) { number } else { number - 1 }
        var xRange = this.xRange
        var mRange = this.mRange
        var aRange = this.aRange
        var sRange = this.sRange
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
        return getRangeSize('x').toLong() * getRangeSize('m') * getRangeSize('a') * getRangeSize('s')
    }

    fun getRangeSize(letter: Char): Int {
        return when(letter) {
            'x' -> xRange.last - xRange.first + 1 // + 1 because using inclusive ranges
            'm' -> mRange.last - mRange.first + 1
            'a' -> aRange.last - aRange.first + 1
            's' -> sRange.last - sRange.first + 1
            else -> throw IllegalArgumentException("Bad letter: char - $letter")
        }
    }
}