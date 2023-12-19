package year23.day19

class Workflow (
    val name: String,
    val letters: List<Char>,
    val isGreaterThans: List<Boolean>,
    val numbers: List<Int>,
    val destinations: List<String>,
    val lastDestination: String
) {
    override fun toString(): String {
        return "Workflow(name='$name', \n\tletters=$letters, \n" +
                "\tisGreaterThan=$isGreaterThans, \n" +
                "\tnumber=$numbers, \n" +
                "\tdestinations=$destinations, \n" +
                "\tlastDestination='$lastDestination')"
    }
    fun getDestination(part: Part): String {
        var index = 0
        while (index < letters.size) {
            if (isGreaterThans[index]) {
                if (part.getValue(letters[index]) > numbers[index]) {
                    return destinations[index]
                }
            } else {
                if (part.getValue(letters[index]) < numbers[index]) {
                    return destinations[index]
                }
            }
            index++
        }
        return lastDestination
    }
}