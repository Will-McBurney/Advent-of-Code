/**
 * Conversion divisorfrom NanoSeconds to MicroSeconds
 */
const val CONVERSION_DIVISOR = 1000

class AoCResultPrinter(
    private val year: Int,
    private val day: Int
) {
    private var startMicroSeconds: Long = -1
    private var setupEndMicroSeconds: Long = -1
    private var part1EndMicroSeconds: Long = -1
    private var part2EndMicroSeconds: Long = -1

    init {
        startMicroSeconds = getCurrentTimeMicroSeconds()
    }

    fun endSetup() {
        setupEndMicroSeconds = getCurrentTimeMicroSeconds()
    }

    fun endPart1() {
        part1EndMicroSeconds = getCurrentTimeMicroSeconds()
    }

    fun endPart2() {
        part2EndMicroSeconds = getCurrentTimeMicroSeconds()
    }

    private fun getCurrentTimeMicroSeconds() = System.nanoTime() / CONVERSION_DIVISOR

    fun printResults(part1Result: Any, part2Result: Any) {
        println("""
            =====Advent of Code: 20$year Day $day=====
            
            Setup time: ${setupEndMicroSeconds - startMicroSeconds} μs
            
            Part 1 Result: $part1Result     Time: ${part1EndMicroSeconds - setupEndMicroSeconds} μs
            Part 2 Result: $part2Result     Time: ${part2EndMicroSeconds - part1EndMicroSeconds} μs
            
            Total Elapsed Time: ${part2EndMicroSeconds - startMicroSeconds} μs
            """.trimIndent()
        )
    }
}