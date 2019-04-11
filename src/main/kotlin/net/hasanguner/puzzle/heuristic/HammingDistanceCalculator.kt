package net.hasanguner.puzzle.heuristic

class HammingDistanceCalculator : HeuristicCalculator {

    override fun calculate(current: Array<IntArray>, goal: Array<IntArray>): Int {
        val flattenedGoal = goal.flatMap(IntArray::toList)
        return current.flatMap(IntArray::toList)
            .filterIndexed { index, it -> it != flattenedGoal[index] }
            .size
    }
}