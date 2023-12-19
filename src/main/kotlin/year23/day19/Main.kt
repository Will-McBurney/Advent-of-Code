package year23.day19

fun main() {
    val startTime = System.currentTimeMillis()

    //Read input
    val reader = object {}.javaClass.getResourceAsStream("input.txt")!!.bufferedReader()
    val lines = reader.readLines()
    val workflows: Map<String, Workflow> = getWorkflows(lines)
    val parts: List<Part> = getParts(lines)
    val readEndTime = System.currentTimeMillis()

    //Do Part 1
    val part1Result = getPart1Result(workflows, parts)
    val part1EndTime = System.currentTimeMillis()

    //Do Part 2
    val part2Result = getPart2Result(workflows)
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
        val rulesText = line.split("{")[1].removeSuffix("}").split(",")
        val lastDestination = rulesText.last()
        val destinations = rulesText.subList(0, rulesText.size - 1).map { rule -> rule.split(":")[1] }
        val rules: List<Rule> = rulesText.subList(0, rulesText.size - 1)
            .map { rule -> rule.split(":")[0] }
            .map { string ->
                if (string.contains(">")) {
                    val letter = string.split(">")[0].first()
                    val number = string.split(">")[1].toInt()
                    return@map Rule(letter, Condition.GREATER_THAN, number)
                } else {
                    val letter = string.split("<")[0].first()
                    val number = string.split("<")[1].toInt()
                    return@map Rule(letter, Condition.LESS_THAN, number)
                }
            }
        workflowMap[name] = Workflow(name, rules, destinations, lastDestination)
        lineIndex++
    }
    return workflowMap
}

fun getPart1Result(workflows: Map<String, Workflow>, parts: List<Part>): Long {
    return parts.stream().filter { part:Part -> isAccepted(part, workflows)}
        .mapToLong {part:Part -> part.getSum()}
        .sum()
}

private fun isAccepted(
    part: Part,
    workflows: Map<String, Workflow>
): Boolean {
    var workflowLabel = "in"
    while (workflowLabel != "A" && workflowLabel != "R") {
        workflowLabel = workflows[workflowLabel]!!.getDestination(part)
    }
    return workflowLabel == "A"
}



fun getPart2Result(workflows: Map<String, Workflow>): Long {
    return workflowSearch(workflows, "in", PartRange((1..4000), (1..4000), (1..4000), (1..4000)))
}

fun workflowSearch(workflows: Map<String, Workflow>, workflowName: String, startingPartRange: PartRange): Long {
    if ("A" == workflowName) {
        return startingPartRange.getPartPossibilities()
    }
    if ("R" == workflowName) {
        return 0
    }
    var sum = 0L
    val workflow = workflows[workflowName]!!
    val rules = workflow.rules
    var partRange = startingPartRange
    for (index in rules.indices) {
        val rule = rules[index]
        if (partRange.isFullRangeTrue(rule)) {
            sum += workflowSearch(workflows, workflow.destinations[index], partRange)
            partRange = PartRange.getEmptyPartRange()
            break
        }
        if (partRange.isFullRangeFalse(rule)) {
            continue
        }
        assert(partRange.isSplitNeeded(rule))
        val splitPartRanges = partRange.splitRange(rule)
        if (rule.isGreaterThan()) {
            partRange = splitPartRanges.first
            sum += workflowSearch(workflows, workflow.destinations[index], splitPartRanges.second)
        } else {
            partRange = splitPartRanges.second
            sum += workflowSearch(workflows, workflow.destinations[index], splitPartRanges.first)
        }
    }
    if (!PartRange.isEmptyPartRange(partRange)) {
        sum += workflowSearch(workflows, workflow.lastDestination, partRange)
    }
    return sum
}