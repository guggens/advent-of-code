import java.util.function.IntPredicate
import java.util.stream.Collectors.toMap

val cubes = mapOf(Pair("red", 12), Pair("green", 13), Pair("blue", 14))

fun main() {
    fun gameNumber(it: String): Int {
        return it.substring(5,it.indexOf(":")).toInt()
    }

    fun part1(input: List<String>): Int {

        var sum = 0;

        input.forEach {
            val game = gameNumber(it)
            var gamePossible = true
            val draws = it.substring(it.indexOf(":")+1).split(";")

            draws.forEach { draw ->
                val colors = draw.split(",").map { color -> color.trim() }
                colors.forEach{ color ->
                    val count = color.substring(0, color.indexOf(" ")).toInt()
                    val colorFound = color.substring(color.indexOf(" ")+1)

                    println("'$colorFound':'$count'")
                    if (count > cubes.getValue(colorFound)) {
                        gamePossible = false
                    }

                }
            }

            if (gamePossible) {
                sum += game
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {

        var sum = 0;

        input.forEach {
            val game = gameNumber(it)
            val draws = it.substring(it.indexOf(":")+1).split(";")

            val neededCubes = mutableMapOf(Pair("red", 1), Pair("blue", 1), Pair("green", 1))
            draws.forEach { draw ->
                val colors = draw.split(",").map { color -> color.trim() }
                colors.forEach{ color ->
                    val count = color.substring(0, color.indexOf(" ")).toInt()
                    val colorFound = color.substring(color.indexOf(" ")+1)

                    val minValue = neededCubes.getValue(colorFound)
                    if (count > minValue) {
                        neededCubes[colorFound] = count
                    }
                }
            }

            val power = neededCubes.getValue("red") * neededCubes.getValue("green") * neededCubes.getValue("blue")
            sum += power
        }

        return sum
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")

    val sum1 = part1(testInput)
    println(sum1)

    val sum2 = part2(testInput)
    println(sum2)
}