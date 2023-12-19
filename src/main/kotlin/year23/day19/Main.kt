package year23.day19

import java.util.function.Predicate

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val workflows: Map<String, Workflow> = getWorkflows(lines);
    val parts: List<Part> = getParts(lines)
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(workflows, parts)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result()
    val part2EndTime = System.currentTimeMillis()

    //Display output
    println(
        """
        |Read Time: %10d ms
        |
        |Part One:  %10d - Time %6d ms
        |Part Two:  %10d - Time %6d ms
        |
        |Total time - ${part2EndTime - startTime}ms
        |""".trimMargin().format(readEndTime - startTime,
            part1Result,
            part1EndTime - readEndTime,
            part2Result,
            part2EndTime - part1EndTime)
    )
}

fun getParts(lines: List<String>): List<Part> {
    val parts: MutableList<Part> = mutableListOf()
    var lineIndex = 0
    while(true) {
        if (lines[lineIndex].isBlank()) {break}
        lineIndex++
    }
    lineIndex++
    while(lineIndex < lines.size) {
        val split = lines[lineIndex].trim('{').trim('}').split(",")
        parts.add(Part(split[0].substringAfter("=").toInt(),
            split[1].substringAfter("=").toInt(),
            split[2].substringAfter("=").toInt(),
            split[3].substringAfter("=").toInt()))
        lineIndex++
    }
    return parts
}

fun getWorkflows(lines: List<String>): Map<String, Workflow> {
    val workflowMap: MutableMap<String, Workflow> = mutableMapOf()
    var lineIndex = 0
    while(true) {
        val line = lines[lineIndex]
        if (line.isBlank()) {
            break
        }
        val name = line.split("{")[0]
        val rules = line.split("{")[1].removeSuffix("}").split(",")
        val lastDestination = rules.last()
        val destinations = rules.subList(0, rules.size - 1).map { rule -> rule.split(":")[1] }
        val predicates: List<Predicate<Part>> = rules.subList(0, rules.size - 1)
            .map { rule -> rule.split(":")[0] }
            .map { string ->
                println(string)
                if (string.contains(">")) {
                    val letter = string.split(">")[0].first()
                    val number = string.split(">")[1].toInt()
                    return@map Predicate { p: Part -> p.getValue(letter) > number }
                } else {
                    val letter = string.split("<")[0].first()
                    val number = string.split("<")[1].toInt()
                    return@map Predicate { p: Part -> p.getValue(letter) < number }
                }
            }
        workflowMap[name] = Workflow(name, predicates, destinations, lastDestination)
        lineIndex++
    }
    return workflowMap
}

fun getPart1Result(workflows: Map<String, Workflow>, parts: List<Part>): Long {
    return parts.parallelStream().filter { part:Part ->
        var workflow = "in"
        while (workflow != "A" && workflow != "R") {
            workflow = getDestination(part, workflows[workflow]!!)
            println(workflow)
        }
        return@filter workflow == "A"
    }.mapToLong {part:Part -> part.x.toLong() + part.m + part.a + part.s}
        .sum()
}

fun getDestination(part: Part, workflow: Workflow): String {
    var index = 0
    while (index < workflow.rules.size) {
        val predicate: Predicate<Part> = workflow.rules[index]
        if (predicate.test(part)) {
            return workflow.destinations[index]
        }
        index++
    }
    return workflow.lastDestination
}

fun getPart2Result(): Int {
    return 0
}