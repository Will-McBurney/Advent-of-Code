package year15.day7

import java.io.BufferedReader

fun main() {
    val startTime = System.currentTimeMillis()
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val gateList = buildGateList(reader)
    val result1 = getPart1Result(gateList)
    val result2 = getPart2Result(gateList, result1)
    val endTime = System.currentTimeMillis()
    print("""
        Part1: $result1
        Part2: $result2 
        Calculation time - ${endTime - startTime}ms"""
        .trimIndent())
}

fun getPart1Result(gateList: List<Gate>): UShort {
    val wireMap = WireMap()
    while (wireMap.getWireValue("a") == null ) {
        gateList.filter { it.isResolvable(wireMap) }
            .forEach {
                wireMap.addWireValue(it.getResultWireName(), it.getResultValue(wireMap))
        }
    }

    return wireMap.getWireValue("a")!!
}

fun getPart2Result(gateList: List<Gate>, aResult: UShort): UShort {
    val wireMap = WireMap()
    wireMap.addWireValue("b", aResult)
    while (wireMap.getWireValue("a") == null ) {
        gateList.filter { it.isResolvable(wireMap) }
            .forEach {
                wireMap.addWireValue(it.getResultWireName(), it.getResultValue(wireMap))
            }
    }
    return wireMap.getWireValue("a")!!
}

fun buildGateList(reader: BufferedReader): List<Gate> {
    val gateList = mutableListOf<Gate>()
    reader.lines()
        .filter(String::isNotEmpty)
        .map(::lineToGate)
        .forEach(gateList::add)
    return gateList
}

val ASSIGNMENT_GATE_PATTERN = "([0-9]+|[a-z]+) -> [a-z]+".toRegex()
val NOT_GATE_PATTERN = "NOT [a-z]+ -> [a-z]+".toRegex()

fun lineToGate(line: String): Gate {
    if (line.matches(ASSIGNMENT_GATE_PATTERN )) {
        return AssignmentGate(line)
    }
    if (line.matches(NOT_GATE_PATTERN)) {
        return NotGate(line)
    }
    return TwoOperandGate(line)
}
