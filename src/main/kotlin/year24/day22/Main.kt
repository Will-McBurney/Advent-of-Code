package year24.day22

import AoCResultPrinter
import Reader

const val year: Int = 24
const val day: Int = 22

fun main() {
    val printer = AoCResultPrinter(year, day)
    
    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    
    val initSecretNumbers = lines.map { it.toLong() }
    
    printer.endSetup()
    
    //Do Part 1
    val part1Result = getPart1Result(initSecretNumbers, 2000)
    printer.endPart1()
    
    //Do Part 2
    val part2Result = getPart2Result(initSecretNumbers, 2000)
    printer.endPart2()
    
    //Display output
    printer.printResults(part1Result, part2Result)
}


fun getPart1Result(initSecretNumbers: List<Long>, repetitions: Int): Long {
    var secretNumbers = initSecretNumbers.toMutableList()
    secretNumbers.parallelStream().mapToLong { sn ->
        var currentNumber = sn
        repeat(repetitions) {
            currentNumber = getNextNumber(currentNumber)
        }
        currentNumber
    }
    return secretNumbers.map { it.toLong() }.sum()
}

fun getPart2Result(initSecretNumbers: List<Long>, repetitions: Int): Int {
    val sequenceMaps = initSecretNumbers.parallelStream().map {
        getSequenceMap(it, repetitions)
    }.toList()
    val sequenceSums = sequenceMaps.fold(mutableMapOf<Int, Int>()) { acc, item ->
        item.entries.forEach { (key, value) ->
            acc[key] = value + acc.getOrDefault(key, 0)
        }
        acc
    }
    val bestSequence = sequenceSums.maxBy { it.value }
    println(bestSequence)
    return bestSequence.value
}

fun getSequenceMap(startingNumber: Long, repetitions: Int):Map<Int, Int> {
    val numberList = getNumberList(startingNumber, repetitions)
    val sequenceMap = mutableMapOf<Int, Int>()
    numberList.windowed(5)
        .map {
            it.map { n -> (n % 10).toInt() }
        }
        .map {
            it.windowed(2).map { it[1] - it[0] }.toList() to (it[4])
        }
        .forEach { (sequence, score) ->
            val hash = hashSequence(sequence)
            if (!sequenceMap.contains(hash))
                sequenceMap[hash] = score
        }
    return sequenceMap
}

fun getNumberList(startingNumber: Long, sequenceLength: Int = 2000): List<Long> {
    val numberList = ArrayList<Long>(2001)
    numberList.add(startingNumber)
    var currentNumber = startingNumber
    repeat(sequenceLength) {
        currentNumber = getNextNumber(currentNumber)
        numberList.add(currentNumber)
    }
    return numberList
}

fun hashSequence(sequence: List<Int>): Int {
    require(sequence.size == 4)
    return (sequence[0] shl(15)) + (sequence[1] shl 10) + (sequence[2] shl 5) + (sequence[3])
}

fun getNextNumber(currentNumber: Long): Long {
    var secretNumber = (currentNumber xor (currentNumber shl (6))) and (16777215)
    secretNumber = (secretNumber xor (secretNumber shr (5))) and (16777215)
    return (secretNumber xor (secretNumber shl (11))) and (16777215)
}
