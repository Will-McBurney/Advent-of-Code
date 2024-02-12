package year17.day07

import AoCResultPrinter

const val year: Int = 17
const val day: Int = 7

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val programs: Set<Program> = getPrograms(lines)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(programs)
    printer.endPart1()

    //Do Part 2
    val part2Result = getPart2Result(programs)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

data class Program (
    val name: String,
    val weight: Int,
    val supporting: List<String>
)

fun getPrograms(lines: List<String>): Set<Program> {
    val programs: MutableSet<Program> = mutableSetOf()
    for (line in lines) {
        line.split(" ")
        val name = line.split(" ")[0]
        val weight = line.substringAfter("(").substringBefore(")").toInt()
        var supportedPrograms: List<String> = emptyList()
        if (line.contains("-> ")) {
            supportedPrograms = line.substringAfter("-> ").split(", ")
        }
        programs.add(Program(name, weight, supportedPrograms))
    }
    return programs
}



fun getPart1Result(programs: Set<Program>): String {
    val remaining: MutableSet<Program> = programs.toMutableSet()
    val visited: MutableMap<String, Program> = mutableMapOf()

    programs.filter { p: Program -> p.supporting.isEmpty() }
        .forEach { p: Program ->
            visited[p.name] = p
            remaining.remove(p)
        }

    while (remaining.size > 1) {
        for (program in remaining.toSet()) {
            if (program.supporting.all { name -> visited.keys.contains(name) }) {
                visited[program.name] = program
                remaining.remove(program)
            }
        }
    }
    return remaining.first().name
}

fun getPart2Result(programs: Set<Program>) {
    val remaining: MutableSet<Program> = programs.toMutableSet()
    val visited: MutableMap<String, Program> = mutableMapOf()
    val weight: MutableMap<String, Int> = mutableMapOf()
    val levels: MutableMap<String, Int> = mutableMapOf()

    var currentLevel = 0

    programs.filter { p: Program -> p.supporting.isEmpty() }
        .forEach { p: Program ->
            visited[p.name] = p
            weight[p.name] = p.weight
            levels[p.name] = currentLevel
            remaining.remove(p)
        }

    while (remaining.isNotEmpty()) {
        currentLevel++
        for (program in remaining.toSet()) {
            if (program.supporting.all { name -> visited.keys.contains(name) }) {
                visited[program.name] = program
                val supportedWeight = weight.filter { entry -> entry.key in program.supporting }
                    .values.sum()
                weight[program.name] = program.weight + supportedWeight
                remaining.remove(program)
                levels[program.name] = currentLevel
            }
        }
    }

    for (name in visited.keys) {
        val supporting = visited[name]!!.supporting
        if (supporting.isEmpty() ) {
            continue
        }
        val targetWeight = weight[supporting.first()]!!
        if (supporting.all { supportingName -> weight[supportingName]!! == targetWeight }) {
            continue
        }
        println("$name - ${weight.getOrDefault(name, -1)} - ${levels[name]}")
        for (s in visited[name]!!.supporting) {
            println("\t$s - ${weight.getOrDefault(s, -1)}")
        }
    }
}
