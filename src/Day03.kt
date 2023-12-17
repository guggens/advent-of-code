val sample1 =
    "311...672...34...391.....591......828.......................738....................223....803..472..................................714.840."
val sample2 =
    ".......*...........*.....*...........*........631%...703.......*..12....652.................*.$............368.769*148.................*...."
val sample3 =
    "....411...........2....837.121........511.745...........*.48.422.@.........@.............311........887......*................457........595"

fun part1(input: List<String>): Int {
    var sum = 0;


    val lines = input.toTypedArray()

    for (lineIndex in lines.indices) {
        var number = ""
        val currentLine = lines[lineIndex]

        print("${lineIndex+1}")

        for (i in currentLine.indices) {

            if (currentLine[i].isDigit()) {
                number += currentLine[i]
            }

            if (!currentLine[i].isDigit() || i == currentLine.indices.last) {

                val endOfRowIsNumber = i == currentLine.indices.last && currentLine[i].isDigit()
                //TODO: Bug is that we ignore end of line numbers, like 253 in row 26, cause there is no follow up char.
                if (number.isNotEmpty()) {

                    val indexOfCharBeforeNumber = i - number.length - 1
                    val hasSpecialCharBeforeNumber =
                        indexOfCharBeforeNumber >= 0 && hasSymbol(currentLine[indexOfCharBeforeNumber].toString())

                    val hasSpecialCharAfterNumber = hasSymbol(currentLine[i].toString())

                    val hasSpecialCharAbove = lineHasSymbol(
                        lines = lines,
                        lineIndex = lineIndex - 1,
                        from = indexOfCharBeforeNumber + (if (endOfRowIsNumber) 1 else 0),
                        to = i + 1
                    )

                    val hasSpecialCharBelow = lineHasSymbol(
                        lines = lines,
                        lineIndex = lineIndex + 1,
                        from = indexOfCharBeforeNumber + (if (endOfRowIsNumber) 1 else 0),
                        to = i + 1
                    )

                    if (hasSpecialCharAbove || hasSpecialCharBelow || hasSpecialCharAfterNumber || hasSpecialCharBeforeNumber) {
                        print("add $number!")
                        sum += number.toInt()
                    }
                    number = ""
                }
            }



        }
        print("=$sum\n")

    }
    return sum
}

fun hasSymbol(s: String): Boolean {
    return s.filterNot { it == '.' }.filterNot { it.isDigit() }.isNotEmpty()
}

fun lineHasSymbol(lineIndex: Int, from: Int, to: Int, lines: Array<String>): Boolean {
    if (lineIndex < 0) return false
    if (lineIndex >= lines.size) return false

    val line = lines[lineIndex]

    val fromIndex = if (from < 0) 0 else from
    val toIndex = if (to > line.length) line.length else to

    return hasSymbol(line.substring(fromIndex, toIndex))
}

fun part2(input: List<String>): Int {

    var sum = 0;

    return sum
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val sum1 = part1(testInput)
    println(sum1)
//
//    val sum2 = part2(testInput)
//    println(sum2)

}
