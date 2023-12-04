import java.util.function.IntPredicate

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 1000)

    var sum = 0
    testInput.forEach { line ->
        var firstDigit: Int? = null
        var lastDigit: Int? = null

        for (i in 0 .. line.length-1) {

            if (line[i].isDigit()) {
                if (firstDigit == null) {
                    firstDigit = line[i].digitToInt()
                    lastDigit = line[i].digitToInt()
                } else {
                    lastDigit = line[i].digitToInt()
                }
            }
        }

        if (firstDigit != null && lastDigit != null) {
            val increment = Integer.valueOf("$firstDigit$lastDigit")
            println(increment)
            sum += increment
        }
    }
    println(sum)
}