package year15.day07

interface Gate {
    fun isResolvable(wireMap: WireMap): Boolean
    fun getResultWireName(): String
    fun getResultValue(wireMap: WireMap): UShort
}

class TwoOperandGate(
    private val gateString: String
) : Gate {
    private var operand1: String = ""
    private var operation: String = ""
    private var operand2: String = ""
    private var resultWire: String = ""

    init {
        val split = gateString.split("->")
        val input = split[0].trim().split(" ")
        if (input.size != 3) {
            throw IllegalArgumentException(gateString)
        }
        resultWire = split[1].trim()
        operand1 = input[0].trim()
        operation = input[1].trim().uppercase()
        operand2 = input[2].trim()
    }

    fun getOperand1(): String = operand1
    fun getOperand2(): String = operand2
    fun getOperation(): String = operation

    private fun isUShort(string: String): Boolean {
        return string.toUShortOrNull() != null
    }

    override fun isResolvable(wireMap: WireMap): Boolean {
        return (isUShort(operand1) || wireMap.contains(operand1)) &&
                (isUShort(operand2) || wireMap.contains(operand2))
    }

    override fun getResultWireName(): String {
        return resultWire
    }

    override fun getResultValue(wireMap: WireMap): UShort {

        val op1 = if (isUShort(operand1)) operand1.toUShort() else wireMap.getWireValue(operand1)!!
        val op2 = if (isUShort(operand2)) operand2.toUShort() else wireMap.getWireValue(operand2)!!

        return when (operation) {
            "AND" -> op1 and op2
            "OR" -> op1 or op2
            "LSHIFT" -> (op1.toInt() shl op2.toInt()).toUShort() // god this seems bad
            "RSHIFT" -> (op1.toInt() shr op2.toInt()).toUShort()
            else -> throw IllegalStateException()
        }

    }
}

class NotGate(
    private val gateString: String
) : Gate {
    private var inputWire: String = ""
    private var outputWire: String = ""

    init {
        val split = gateString.trim().split("->")
        outputWire = split[1].trim()
        val inputSplit = split[0].trim().split(" ")
        if (inputSplit[0].trim().lowercase() != "not") {
            throw IllegalArgumentException()
        }
        inputWire = inputSplit[1].trim()
    }

    override fun isResolvable(wireMap: WireMap): Boolean = wireMap.contains(inputWire)

    override fun getResultWireName(): String = outputWire

    override fun getResultValue(wireMap: WireMap): UShort {
        if (!isResolvable(wireMap)) {
            throw IllegalStateException()
        }
        val op = wireMap.getWireValue(inputWire)!!
        return op.inv()
    }
}

class AssignmentGate(
    private val gateString: String
) : Gate {
    private var inputWire = ""
    private var resultWire = ""

    init {
        val split = gateString.trim().split("->")
        inputWire = split[0].trim()
        resultWire = split[1].trim()
    }

    private fun isUShort(string: String): Boolean {
        return string.toUShortOrNull() != null
    }

    override fun isResolvable(wireMap: WireMap): Boolean =
        isUShort(inputWire) || wireMap.contains(inputWire)

    override fun getResultWireName(): String = resultWire

    override fun getResultValue(wireMap: WireMap): UShort {
        if (isUShort(inputWire)) {
            return inputWire.toUShort()
        }
        return wireMap.getWireValue(inputWire)!!
    }

}