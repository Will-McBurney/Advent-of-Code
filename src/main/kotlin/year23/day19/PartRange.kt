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

    fun isSplitNeeded(rule: Rule): Boolean {
        val targetNumber = if (rule.isGreaterThan()) { rule.number } else { rule.number - 1 }
        val range = getRange(rule.letter)
        return range.first <= targetNumber && range.last > targetNumber
    }

    fun isFullRangeTrue(rule: Rule): Boolean {
        return if (rule.isGreaterThan()) {
            getRange(rule.letter).first > rule.number
        } else {
            getRange(rule.letter).last < rule.number
        }
    }

    fun isFullRangeFalse(rule:Rule): Boolean {
        return if (rule.isGreaterThan()) {
            getRange(rule.letter).last <= rule.number
        } else {
            getRange(rule.letter).first >= rule.number
        }
    }

    /**
     * Split a PartRange into two PartRanges. The "lower" value comes first, the "higher" value comes second
     */
    fun splitRange(rule: Rule): Pair<PartRange, PartRange> {
        val maxLowerRange = if (rule.isGreaterThan()) { rule.number } else { rule.number - 1 }
        val xRange = this.xRange
        val mRange = this.mRange
        val aRange = this.aRange
        val sRange = this.sRange
        when(rule.letter) {
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
        throw IllegalArgumentException("Invalid letter: ${rule.letter}")
    }

    fun getPartPossibilities(): Long {
        return "xmas".map { letter -> getRangeSize(letter).toLong() }.reduce(Long::times)
    }

    fun getRangeSize(letter: Char): Int {
        val range = getRange(letter)
        return range.last - range.first + 1
    }
}