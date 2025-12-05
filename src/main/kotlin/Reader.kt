class Reader {
    companion object {
        private val linesMemo: MutableMap<String, List<String>> = mutableMapOf()
        fun getLines(year: Int, day: Int, filename: String): List<String> {
            val resourceFilename = getResourceFilename(year, day, filename)
            println("Opening file: $resourceFilename")

            if (!linesMemo.containsKey(resourceFilename)) {
                linesMemo[resourceFilename] = Reader::class.java
                    .getResourceAsStream(resourceFilename)!!
                    .bufferedReader()
                    .readLines()
            }
            return linesMemo[resourceFilename]!!
        }

        private fun getResourceFilename(year: Int, day: Int, filename: String): String {
            return "year$year/day%02d/$filename".format(day)
        }
    }
}