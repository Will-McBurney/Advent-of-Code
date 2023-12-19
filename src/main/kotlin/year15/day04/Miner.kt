package year15.day04

import java.math.BigInteger
import java.security.MessageDigest


val myKey = "ckczppom"

fun main() {
    val startTime = System.currentTimeMillis();
    for (i in 1..Integer.MAX_VALUE) {
        md5Check(myKey, i, startTime)
    }
    val endTime = System.currentTimeMillis();

}

private fun md5Check(myKey: String, i: Int, startTime: Long) {
    val input = myKey + i.toString()
    val md = MessageDigest.getInstance("MD5")
    val result = md.digest(input.toByteArray())
    val bigInt = BigInteger(1, result)
    val fullString = bigInt.toString(16).padStart(32, '0')
    if (fullString.startsWith("000000")) {
        val endTime = System.currentTimeMillis();
        println("$i - Time Elapse: ${endTime - startTime}ms")
    }
}