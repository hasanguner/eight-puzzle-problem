package net.hasanguner.puzzle

/**
 * A generic puzzle solver to solve 8-puzzle problem.
 * @param board represents the 8-puzzle board.
 */
abstract class PuzzleSolver(val board: PuzzleBoard) {

    /**
     * Returns minimum number of moves to solve the puzzle board
     */
    abstract fun moves(): Int

    /**
     * Returns shortest board sequence to the solution
     */
    abstract fun solution(): Iterable<PuzzleBoard>


}