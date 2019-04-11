package net.hasanguner.puzzle.heuristic

import net.hasanguner.puzzle.Matrix.positionOf

class ManhattanDistanceCalculator : HeuristicCalculator {

    override fun calculate(current: Array<IntArray>, goal: Array<IntArray>): Int =
        current.foldIndexed(0) { i, cost, row ->
            cost + row.foldIndexed(0) { j, acc, num ->
                val (gi, gj) = goal.positionOf(num)
                acc + Math.abs(i - gi) + Math.abs(j - gj)
            }
        }
}