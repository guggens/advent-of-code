package day16

import readInput
import javax.swing.text.html.HTML.Tag.P


fun main() {
    fun parseInput(input: List<String>): Array<Array<Tile>> {
        var list:MutableList<Array<Tile>> = mutableListOf()
        input.forEach { line ->
            list.add( line.toCharArray().map { Tile(it) }.toTypedArray())
        }
        return list.toTypedArray()
    }

    fun getEnergizedForBeam(startBeam: Beam): Int {
        var sum = 0
        uniqueBeams.clear()

        var beams = mutableListOf(startBeam)
        do {
            val newBeams = mutableListOf<Beam>()
            val iterator = beams.iterator()
            while (iterator.hasNext()) {
                val nextBeam = iterator.next()
                newBeams.addAll(startBeam(nextBeam))
            }
            beams = newBeams
        } while (beams.isNotEmpty())

        startBeam.field.forEach { line ->
            line.forEach { tile ->
                if (tile.energized) sum += 1
            }
        }

        println("Beam: x: ${startBeam.position.x} y: ${startBeam.position.y} direction:${startBeam.direction.name} sum:${sum}")
        return sum
    }

    fun part1(input: List<String>): Int {
        val field = parseInput(input)
        val startBeam = Beam(position = Position(0,0), direction = Direction.RIGHT, field)
        return getEnergizedForBeam(startBeam)
    }


    fun part2(input: List<String>): Int {

        var max = 0;
        var sum = 0;

        var field = parseInput(input)

        //all beams in down direction
        for (i in field[0].indices) {
            field = parseInput(input)
            sum = getEnergizedForBeam(Beam(position = Position(x=0, y = i), direction = Direction.DOWN, field))
            if (sum > max) max = sum
        }
        for (i in field[0].indices) {
            field = parseInput(input)
            sum = getEnergizedForBeam(Beam(position = Position(x=field.size-1, y = i), direction = Direction.UP, field))
            if (sum > max) max = sum
        }
        for (i in field.indices) {
            field = parseInput(input)
            sum = getEnergizedForBeam(Beam(position = Position(x=i, y = 0), direction = Direction.RIGHT, field))
            if (sum > max) max = sum
        }
        for (i in field.indices) {
            field = parseInput(input)
            sum = getEnergizedForBeam(Beam(position = Position(x=field[0].size-1, y = 0), direction = Direction.LEFT, field))
            if (sum > max) max = sum
        }

        return max;
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day16/Day16_test")
    val sum1 = part1(testInput)
    println(sum1)

    val sum2 = part2(testInput)
    println(sum2)

}

data class Tile(val char: Char, var energized: Boolean = false)

enum class Direction {
    LEFT, RIGHT, UP, DOWN;
}

data class Position(var x: Int, var y: Int)

val uniqueBeams = mutableSetOf<String>()

fun startBeam(beam: Beam): List<Beam> {
    if (beam.positionOutOfBounds()) return emptyList()
    if (!uniqueBeams.contains(beam.uniqueIdentifier())) {
        uniqueBeams.add(beam.uniqueIdentifier())
        return beam.travel()
    }
    return emptyList()
}

class Beam(var position: Position, var direction: Direction, val field: Array<Array<Tile>>, val id: Int = 1) {

    fun uniqueIdentifier() = "${position.x}${position.y}$direction"

    fun travel(): List<Beam> {

        val currentTile = field[position.x][position.y]
        //println("Beam ${this.id}: x: ${position.x} y: ${position.y} direction:${direction.name} tile:${currentTile.char}")

        currentTile.energized = true
        when (currentTile.char) {
            '/' -> {
                when (direction) {
                    Direction.RIGHT -> direction = Direction.UP
                    Direction.DOWN -> direction = Direction.LEFT
                    Direction.LEFT -> direction = Direction.DOWN
                    Direction.UP -> direction = Direction.RIGHT
                }
            }
            '\\' -> {
                when (direction) {
                    Direction.RIGHT -> direction = Direction.DOWN
                    Direction.DOWN -> direction = Direction.RIGHT
                    Direction.LEFT -> direction = Direction.UP
                    Direction.UP -> direction = Direction.LEFT
                }
            }
            '-' -> {
                when (direction) {
                    Direction.DOWN, Direction.UP -> {
                        return listOf(
                            Beam(position.copy(), Direction.RIGHT, field, id + 1),
                            Beam(position.copy(), Direction.LEFT, field, id + 1)
                        )
                    }
                    else -> {}
                }
            }
            '|' -> {
                when (direction) {
                    Direction.LEFT, Direction.RIGHT -> {
                        return listOf(
                            Beam(position.copy(), Direction.UP, field, id + 1),
                            Beam(position.copy(), Direction.DOWN, field, id + 1)
                        )
                    }
                    else -> {}
                }
            }

            else -> {
                //do nothing on dots
            }
        }

        when (direction) {
            Direction.RIGHT -> position.y++
            Direction.DOWN -> position.x++
            Direction.UP -> position.x--
            Direction.LEFT -> position.y--
        }

        return if (positionOutOfBounds())
            emptyList()
        else {
            travel()
        }
    }

    fun positionOutOfBounds(): Boolean {
        return position.x < 0 || position.y < 0 || position.x == field[0].size || position.y == field[1].size
    }

}