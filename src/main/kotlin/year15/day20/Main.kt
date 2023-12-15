package year15.day20

const val target = 33100000



fun main() {
    val array: Array<Long> = Array<Long>(target*2) {0}
    for (i in 1 .. target) {
        for(counter in 1..50) {
            if (i * counter > target) break
            array[i * counter] = array[i * counter] + i * 11
        }
    }
    println(array.indices.first { array[it] >= target })
}