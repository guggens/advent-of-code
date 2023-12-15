import java.util.function.IntPredicate
import java.util.stream.Collectors.toMap

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { line ->
            var firstDigit: Int? = null
            var lastDigit: Int? = null


            for (i in line.indices) {
                if (line[i].isDigit()) {
                    if (firstDigit == null) {
                        firstDigit = line[i].digitToInt()
                        lastDigit = line[i].digitToInt()
                    } else {
                        lastDigit = line[i].digitToInt()
                    }
                }
            }

            if (firstDigit != null) {
                val increment = Integer.valueOf("$firstDigit$lastDigit")
                println(increment)
                sum += increment
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach { line ->
            val firstDigit = firstDigit(line)
            val lastDigit = lastDigit(line)

            val increment = Integer.valueOf("$firstDigit$lastDigit")
            println(increment)
            sum += increment
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")

    val sum1 = part1(testInput)
    println(sum1)

    val sum2 = part2(testInput)
    println(sum2)
}

data class Digit(val literal: String, val value: Int)

val digits = listOf(
    Digit("one", 1),
    Digit("two", 2),
    Digit("three", 3),
    Digit("four", 4),
    Digit("five", 5),
    Digit("six", 6),
    Digit("seven", 7),
    Digit("eight", 8),
    Digit("nine", 9),
    Digit("1", 1),
    Digit("2", 2),
    Digit("3", 3),
    Digit("4", 4),
    Digit("5", 5),
    Digit("6", 6),
    Digit("7", 7),
    Digit("8", 8),
    Digit("9", 9)
)


fun firstDigit(line: String) : Int {

    val foundNumbers = mapOf<Digit, Int>().toMutableMap()
    for (d in digits.indices) {
        foundNumbers[digits[d]] = line.indexOf(digits[d].literal)
    }

    val filtered = foundNumbers.filterValues { it != -1 }
    val sorted = filtered.toList().sortedBy { (_, value)  -> value}.toMap()

    val key = sorted.keys.first()
    println(key)

    return key.value
}

fun lastDigit(line: String) : Int {

    val foundNumbers = mapOf<Digit, Int>().toMutableMap()
    for (d in digits.indices) {
        foundNumbers[digits[d]] = line.lastIndexOf(digits[d].literal)
    }

    val filtered = foundNumbers.filterValues { it != -1 }
    val sorted = filtered.toList().sortedBy { (_, value)  -> value}.reversed().toMap()

    val key = sorted.keys.first()
    println(key)

    return key.value
}
