package year23.day20

import java.util.*

fun main() {
    val startTime = System.nanoTime()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    var modules = getModules(lines)

    val readEndTime = System.nanoTime()

    //Do Part 1
    val part1Result = getPart1Result(modules, 1000)
    val part1EndTime = System.nanoTime()

    modules = getModules(lines) //reset

    //Do Part 2
    val part2Result = getPart2Result(modules, "rx")
    val part2EndTime = System.nanoTime()

    //Display output
    println("Input read time: ${elapsedMicroSeconds(startTime, readEndTime)} μs\n")
    println(
        "Part 1: %15d - Time %10d  μs"
            .format(part1Result, elapsedMicroSeconds(readEndTime, part1EndTime))
    )
    println(
        "Part 2: %15d - Time %10d  μs\n"
            .format(part2Result, elapsedMicroSeconds(part1EndTime, part2EndTime))
    )
    println("Total time - ${elapsedMicroSeconds(startTime, part2EndTime)} μs")
}

fun getModules(lines: List<String>): Map<String, Module> {
    val moduleMap = lines.associate { line ->
        val outputs = line.substringAfter(" -> ")
            .split(", ")
        if (line.startsWith("broadcaster")) {
            return@associate "broadcaster" to Button(outputs)
        }
        if (line.startsWith("%")) {
            val name = line.substringAfter("%").substringBefore(" -> ")
            return@associate name to FlipFlop(name, outputs)
        }
        if (line.startsWith("&")) {
            val name = line.substringAfter("&").substringBefore(" -> ")
            return@associate name to Conjunction(name, outputs)
        }
        throw IllegalArgumentException("Bad line: $line")
    }

    moduleMap.values.forEach { module ->
        when (module) {
            is FlipFlop -> {
                module.getOutputs().forEach { name ->
                    when (val m = moduleMap[name]!!) {
                        is Conjunction -> m.addInput(module.getName())
                    }
                }
            }
        }
    }

    return moduleMap
}

fun elapsedMicroSeconds(start: Long, end: Long): Long = (end - start) / 1000

fun getPart1Result(modules: Map<String, Module>, buttonPresses: Int): Long {
    var lowPulseCount = 0
    var highPulseCount = 0
    repeat(buttonPresses) { count ->
        //Sender, Pulse, Receiver
        val queue: Queue<Triple<String, Pulse, String>> = LinkedList(listOf(Triple("button", Pulse.LOW, "broadcaster")))
        lowPulseCount++
        while (queue.isNotEmpty()) {
            val nextPulse = queue.poll()
            val senderName = nextPulse.first
            val pulse = nextPulse.second
            val receiverName = nextPulse.third
            val receiver = modules[receiverName] ?: continue
            val receiverPulse = receiver.handlePulse(pulse, senderName)
            when (receiverPulse) {
                Pulse.HIGH -> highPulseCount += receiver.getOutputs().size
                Pulse.LOW -> lowPulseCount += receiver.getOutputs().size
                Pulse.NONE -> continue
            }
            receiver.getOutputs().forEach { outputName ->
                queue.add(Triple(receiverName, receiverPulse, outputName))
            }

        }
    }
    return lowPulseCount * highPulseCount.toLong()
}

const val NOT_FOUND = -1L

fun getPart2Result(modules: Map<String, Module>, moduleName: String): Long {
    var buttonPresses = 0L
    val lastConjunction = modules.values.single { module -> module.getOutputs().contains(moduleName) }.getName()
    val targets = modules.values.filter { module -> module.getOutputs().contains(lastConjunction) }.map { it.getName() }
    val targetHits = targets.associateWith { NOT_FOUND }.toMutableMap()


    while (true) {
        //Sender, Pulse, Receiver
        val queue: Queue<Triple<String, Pulse, String>> = LinkedList(listOf(Triple("button", Pulse.LOW, "broadcaster")))
        buttonPresses++
        while (queue.isNotEmpty()) {
            val nextPulse = queue.poll()
            val senderName = nextPulse.first
            val pulse = nextPulse.second
            val receiverName = nextPulse.third
            if (pulse == Pulse.HIGH && targets.contains(senderName)) {
                if (targetHits[senderName] == NOT_FOUND) {
                    targetHits[senderName] = buttonPresses
                }

                if (targetHits.values.all { it != NOT_FOUND }) {
                    println(targetHits)
                    return lcm(targetHits.keys.map { key -> targetHits[key]!! })
                }
            }
            val receiver = modules[receiverName] ?: continue
            val receiverPulse = receiver.handlePulse(pulse, senderName)
            if (receiverPulse == Pulse.NONE) continue
            receiver.getOutputs().forEach { outputName ->
                queue.add(Triple(receiverName, receiverPulse, outputName))
            }
        }
    }
}

fun gcd(a: Long, b: Long): Long {
    return if (a == 0L) b else gcd(b % a, a)
}

fun lcm(numbers: List<Long>): Long {
    return numbers.fold(1L) { acc, item -> acc * (item / gcd(acc, item)) }
}
