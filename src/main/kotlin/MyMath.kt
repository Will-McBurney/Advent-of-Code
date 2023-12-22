fun gcd(a: Long, b: Long): Long {
    return if (a == 0L) b else gcd(b % a, a)
}

fun gcd(numbers: List<Long>): Long {
    return numbers.reduce { acc, item -> gcd(acc, item) }
}

fun lcm(numbers: List<Long>): Long {
    return numbers.fold(1L) { acc, item -> acc * (item / gcd(acc, item)) }
}

fun posMod(number: Long, divisor: Long): Long {
    var output = (number % divisor)
    if (output < 0) {
        output += number
    }
    return output
}
