package year22.day05

import java.util.*

data class BoxStacks (
    val stackCount: Int
) {
    private val stacks: List<Stack<Char>> = (0 ..< stackCount).map { Stack<Char>() }

    fun movePart1(instruction: Instruction) {
        repeat(instruction.boxCount) {
            stacks[instruction.endStack - 1].push(stacks[instruction.startStack - 1].pop())
        }
    }

    fun movePart2(instruction: Instruction) {
        val tempStack: Stack<Char> = Stack()
        repeat(instruction.boxCount) {
            tempStack.push(stacks[instruction.startStack - 1].pop())
        }
        repeat(instruction.boxCount) {
            stacks[instruction.endStack - 1].push(tempStack.pop())
        }
    }

    fun getMessage(): String {
        return stacks.map { stack -> stack.peek() }.joinToString("")
    }

    fun push(boxIndex: Int, char: Char) {
        stacks[boxIndex - 1].push(char)
    }

    override fun toString(): String {
        return stacks.toString()
    }
}