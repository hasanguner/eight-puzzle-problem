package net.hasanguner.puzzle.heuristic

interface HeuristicCalculator {
    fun calculate(current: Array<IntArray>, goal: Array<IntArray>): Int
}