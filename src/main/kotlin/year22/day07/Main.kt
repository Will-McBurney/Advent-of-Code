package year22.day07

import AoCResultPrinter
import Reader
import java.util.*

const val year: Int = 22
const val day: Int = 7

fun main() {
    val printer = AoCResultPrinter(year, day)

    //Setup
    val inputFilename = "input.txt"
    val lines = Reader.getLines(year, day, inputFilename)
    val rootDirectory = getRootDirectoryStructure(lines)

    printer.endSetup()

    //Do Part 1
    val part1Result = getPart1Result(rootDirectory, 100000L)
    printer.endPart1()

    val discSize = 70000000L
    val updateSize = 30000000L
    val unusedSpace = discSize - rootDirectory.size()
    val spaceToFree = updateSize - unusedSpace

    //Do Part 2
    val part2Result = getPart2Result(rootDirectory, spaceToFree)
    printer.endPart2()

    //Display output
    printer.printResults(part1Result, part2Result)
}

data class FileData(
    val filename: String,
    val size: Long
)

data class Directory(
    val name: String
) {
    val files: MutableList<FileData> = mutableListOf()
    val subdirectories: MutableMap<String, Directory> = mutableMapOf()

    private var sumCache: Long? = null
    fun size(): Long {
        if (sumCache == null) {
            sumCache = files.sumOf { file -> file.size } + subdirectories.values.sumOf { directory -> directory.size() }
        }
        return sumCache!!
    }
}

/**
 * returns root directory
 */
fun getRootDirectoryStructure(lines: List<String>): Directory {
    val rootDirectory = Directory(name = "/")
    val directoryStack: Stack<Directory> = Stack<Directory>()
    directoryStack.push(rootDirectory)
    var inListMode = false

    assert(lines[0].trim() == "cd /")
    for (line in lines.subList(1, lines.size)) {
        val tokens = line.trim().split(" ")
        val currentDirectory = directoryStack.peek()

        if (inListMode && tokens[0] == ("$")) {
            inListMode = false
        }

        if (inListMode) {
            if (tokens[0] == "dir") {
                currentDirectory.subdirectories[tokens[1]] = Directory(name = tokens[1])
            } else {
                val fileData = FileData(filename = tokens[1], size = tokens[0].toLong())
                currentDirectory.files.add(fileData)
            }
        }

        if (!inListMode && tokens[1] == "cd") {
            if (tokens[2] == "..") {
                directoryStack.pop()
            } else {
                directoryStack.push(currentDirectory.subdirectories[tokens[2]]!!)
            }
        }

        if (!inListMode && tokens[1] == "ls") {
            inListMode = true
        }

    }

    return rootDirectory
}


fun getPart1Result(rootDirectory: Directory, maxSize: Long): Long {
    return rootDirectory.subdirectories.values.sumOf{dir -> getPart1Result(dir, maxSize)} +
            if (rootDirectory.size() > maxSize) 0 else rootDirectory.size()
}

fun getPart2Result(rootDirectory: Directory, minSize: Long): Long {
    val rootDirectorySize = rootDirectory.size()
    if (rootDirectorySize < minSize) {
        return Long.MAX_VALUE
    } else {
        if (rootDirectory.subdirectories.isEmpty()) {
            return rootDirectorySize
        }
        return rootDirectorySize.coerceAtMost(
            rootDirectory.subdirectories.values.minOf { dir -> getPart2Result(dir, minSize) }
        )
    }
}
