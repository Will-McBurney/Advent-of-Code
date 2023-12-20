package year23.day20

fun main() {
    val startTime = System.nanoTime()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val modules = getModules(lines)

    val readEndTime = System.nanoTime()

    //Do Part 1
    val part1Result = getPart1Result(modules, 1000)
    val part1EndTime = System.nanoTime()

    //Do Part 2
    val part2Result = getPart2Result()
    val part2EndTime = System.nanoTime()

    //Display output
    println("Input read time: ${elapsedMicroSeconds(startTime, readEndTime)} μs\n")
    println("Part 1: %15d - Time %10d  μs"
        .format(part1Result, elapsedMicroSeconds(readEndTime, part1EndTime)))
    println("Part 2: %15d - Time %10d  μs\n"
        .format(part2Result, elapsedMicroSeconds(part1EndTime, part2EndTime)))
    println("Total time - ${elapsedMicroSeconds(startTime, part2EndTime)} μs")
}

fun getModules(lines: List<String>): Map<String, Module> {
    val moduleMap = lines.associate{ line ->
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

    moduleMap.values.forEach {module ->
        when (module) {
            is FlipFlop -> {
                module.getOutputs().forEach {name ->
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
    var lowPulseCount = 0L
    var highPulseCount = 0L
    var currentTic = 0
    repeat(buttonPresses) {count ->
        println("Button press ${count + 1}")
        println("button -LOW-> broadcaster")
        lowPulseCount++ //pressing the button counts as sending a low pulse to broadcaster
        do {
            modules.values.forEach { module ->
                val pulse = module.getNextPulse()
                if (pulse != Pulse.NONE) {
                    module.getOutputs().forEach {
                        if (pulse == Pulse.HIGH) highPulseCount++
                        if (pulse == Pulse.LOW) lowPulseCount++
                        println("${module.getName()} -$pulse-> $it")
                        if (modules.containsKey(it)) {
                            modules[it]!!.handlePulse(pulse, module.getName())
                        }
                    }
                }
                module.incrementTic()
            }
        } while (modules.values.any { module -> !module.isDone() })
        println("")

        println("LOW: $lowPulseCount - HIGH: $highPulseCount")
        println("")
        reset(modules)
    }
    println("LOW: $lowPulseCount - HIGH: $highPulseCount")
    return lowPulseCount * highPulseCount
}

fun reset(modules: Map<String, Module>) {
    modules.values.forEach{ module -> module.setTic(0) }
}

fun getPart2Result(): Int {
    return 0
}