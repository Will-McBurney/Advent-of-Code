package year24.day24

import AoCResultPrinter
import Reader

const val year: Int = 24
const val day: Int = 24



fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    
    val splitPoint = lines.indexOf("")
    val initialWireValues = lines.subList(0, splitPoint).map { it ->
        it.substringBefore(": ") to (it.substringAfter(": ").toInt() == 1)
    }.toMap()
    
    val logicGates = lines.subList(splitPoint + 1, lines.size).map { getLogicGate(it) }
    
    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(initialWireValues, logicGates)
    printer.endPart1()
    
    val x = getNumberFromStartingLetter(initialWireValues, 'x')
    val y = getNumberFromStartingLetter(initialWireValues, 'y')
    
    println("x:    ${x.toString(2)}")
    println("y:    ${y.toString(2)}")
    println("sum: ${(x+y).toString(2)}")
    println("z:   ${part1Result.toString(2)}")
    
    println()
    
    val gatesOfInterest = logicGates.map { it.output }
        .filterNot { it.startsWith('z') }
        .filter { output -> logicGates.none { it.operand1 == output } }
        .filter { output -> logicGates.none { it.operand2 == output } }
    
    println(gatesOfInterest)
    
    println()
    
    var lastGates = emptyList<String>()
    (0..45).forEach { gateNumber ->
        val currentGate = getDependentWires(logicGates, getGameName(gateNumber))
        println("$gateNumber: ${(currentGate - lastGates).sorted()}")
        lastGates = currentGate + lastGates
    }
    
    
    //Do Part 2
    val part2Result = getPart2Result()
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

fun getGameName(gateNumber: Int): String {
    return if (gateNumber < 10) "z0$gateNumber" else "z$gateNumber"
}

fun getDependentWires(gates: List<LogicGate>, wireName: String): List<String> {
    val dependentGates = gates.filter { it.output == wireName }
    if (dependentGates.isEmpty()) { return emptyList() }
    val dependentWires = dependentGates.map {
        val op1Wires = getDependentWires(gates, it.operand1) + it.operand1
        val op2Wires = getDependentWires(gates, it.operand2) + it.operand2
        return@map op1Wires + op2Wires
    }.flatten()
    
    return dependentWires
}

enum class LogicOperation {
    AND,
    OR,
    XOR
}

class LogicGate(
    val operand1: String,
    val operand2: String,
    val operation: LogicOperation,
    val output: String,
) {
    var isResolved = false
        private set
    
    fun isResolvable(wireValues: Map<String, Boolean>) =
        wireValues.contains(operand1) && wireValues.contains(operand2)
    
    fun resolve(wireValues: Map<String, Boolean>): Boolean {
        isResolved = true
        return when (operation) {
            LogicOperation.AND -> wireValues[operand1]!! and wireValues[operand2]!!
            LogicOperation.OR -> wireValues[operand1]!! or wireValues[operand2]!!
            LogicOperation.XOR -> wireValues[operand1]!! xor wireValues[operand2]!!
        }
    }
    
    override fun toString(): String {
        return "$operand1 $operation $operand2 -> $output"
    }
}

fun getLogicGate(line: String): LogicGate {
    val tokens = line.split(" ")
    return LogicGate(tokens[0], tokens[2], getLogicOperation(tokens[1]), tokens[4])
}

fun getLogicOperation(operation: String): LogicOperation {
    return when(operation.uppercase()) {
        "AND" -> LogicOperation.AND
        "OR" -> LogicOperation.OR
        "XOR" -> LogicOperation.XOR
        else -> throw IllegalArgumentException("Bad logic operation name: $operation")
    }
}

fun getPart1Result(initialWireValues: Map<String, Boolean>, initialLogicGates: List<LogicGate>): Long {
    var remainingLogicGates = initialLogicGates.toList()
    val wireValues = HashMap(initialWireValues)
    while (remainingLogicGates.filter { it.output.startsWith("z") }.isNotEmpty()) {
        remainingLogicGates.filter { it.isResolvable(wireValues) }
            .forEach {
                wireValues[it.output] = it.resolve(wireValues)
            }
        remainingLogicGates = remainingLogicGates.filterNot { it.isResolved }
    }
    return getNumberFromStartingLetter(wireValues, 'z')
}

fun getNumberFromStartingLetter(map: Map<String, Boolean>, startingCharacter: Char = 'z'): Long {
    val zList = map.keys.filter { it.startsWith(startingCharacter) }
        .sorted().reversed()
    
    var sum = 0L
    for (b in zList) {
        sum = if (map[b]!!) {
            (sum shl 1) + 1L
        } else {
            sum shl 1
        }
    }
    return sum
}

fun getPart2Result(): Int {
    return 0
}
