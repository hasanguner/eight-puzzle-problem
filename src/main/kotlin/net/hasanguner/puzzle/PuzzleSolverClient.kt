package net.hasanguner.puzzle

import net.hasanguner.puzzle.heuristic.ManhattanDistanceCalculator
import net.hasanguner.search.algorithm.SearchAlgorithm
import net.hasanguner.search.algorithm.SearchAlgorithmFactory
import java.nio.file.Paths

class PuzzleSolverClient {

    private val heuristicCalculator = ManhattanDistanceCalculator()
    private val search = SearchAlgorithmFactory.create(SearchAlgorithm.ASTAR)

    fun run(filePath: String) {
        readLines(filePath)
            .let { parseBlocks(it) }
            .let { solve(it) }
    }

    private fun solve(blocks: Array<IntArray>) {
        val board = PuzzleBoard(blocks, heuristicCalculator)
        if (!board.solvable) println("Unsolvable puzzle")
        val (moves, solution) = AIPuzzleSolver(search, board).run { moves() to solution() }
        println("Minimum number of moves = $moves")
        solution.forEach { println("(${it.size}x${it.size})\n$it") }
    }

    private fun parseBlocks(lines: List<String>): Array<IntArray> = lines
        .asSequence()
        .map { it.trim() }
        .map { "(\\d)\\s+(\\d)\\s+(\\d)".toRegex().find(it) }
        .filterNotNull()
        .map { it.groupValues.drop(1) }
        .filter { it.size == 3 }
        .map { it.map(String::toInt).toIntArray() }
        .toList()
        .toTypedArray()

    private fun readLines(filePath: String): List<String> =
        Paths.get(filePath)
            .toFile()
            .readLines()
}


fun main(args: Array<String>) {
    val path = args[0]
    PuzzleSolverClient().run(path)
}
