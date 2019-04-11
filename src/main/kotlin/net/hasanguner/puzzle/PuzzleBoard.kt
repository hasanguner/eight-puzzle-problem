package net.hasanguner.puzzle

import net.hasanguner.puzzle.Matrix.positionOf
import net.hasanguner.puzzle.Matrix.swap
import net.hasanguner.puzzle.Matrix.toFlatString
import net.hasanguner.puzzle.Matrix.toMatrixString
import net.hasanguner.puzzle.heuristic.HeuristicCalculator
import net.hasanguner.search.datastructure.Edge
import net.hasanguner.search.datastructure.Node
import java.util.*

/**
 * Represents a 8-puzzle board which consist of 9 blocks in the form of 3x3 Matrix.
 * @property blocks Block tails in the form of 3x3 matrix.
 * @property heuristicCalculator to calculate the heuristic distance up to [PuzzleBoard.GOAL_BLOCKS]}.
 * @constructor Creates an instance of a 8-puzzle board node.
 * @throws IllegalArgumentException if blocks do not contain all tail(number)s in the range of [0..8].
 *
 */
class PuzzleBoard(
    _blocks: Array<IntArray>,
    val heuristicCalculator: HeuristicCalculator
) : Node(
    _blocks.toFlatString()
) {

    private val blocks: Array<IntArray> = _blocks.map { it.copyOf() }.toTypedArray()

    init {
        validateBlocks()
    }

    /**
     * Returns a copy of blocks.
     */
    fun blocks(): Array<IntArray> =
        blocks.map { it.copyOf() }.toTypedArray()

    /**
     * Particular implementation of [Node.heuristic].
     * Returns a heuristic to the [PuzzleBoard.GOAL_BLOCKS] using [heuristicCalculator].
     */
    override val heuristic: Int by lazy {
        heuristicCalculator.calculate(blocks, GOAL_BLOCKS)
    }

    /**
     * Particular implementation of [Node.adjacencies].
     * Returns neighbors as [Edge]s with 0 path cost.
     */
    override val adjacencies: Set<Edge>
        get() = neighbors.map { Edge(0, it) }.toSet()

    /**
     * Returns all possible neighbors based on empty tail's position.
     */
    val neighbors: List<PuzzleBoard> by lazy {
        val (i, j) = emptyTailPosition.let { it.first to it.second }
        val options = mutableListOf<PuzzleBoard>()
        if (i != 0) options += PuzzleBoard(blocks.swap(i, j, i - 1, j), heuristicCalculator)
        if (i != size - 1) options += PuzzleBoard(blocks.swap(i, j, i + 1, j), heuristicCalculator)
        if (j != 0) options += PuzzleBoard(blocks.swap(i, j, i, j - 1), heuristicCalculator)
        if (j != size - 1) options += PuzzleBoard(blocks.swap(i, j, i, j + 1), heuristicCalculator)
        options
    }

    /**
     * Determines whether board can be solved by checking the inversions.
     * Inversion is a pair of tiles (a,b) such that a appears before b, but a>b.
     * Returns 'true' if the total count of inversions is an even number.
     */
    val solvable: Boolean by lazy {
        val flattenedBlocks = blocks.flatMap(IntArray::toList).filter { it != 0 }
        val totalInversion = flattenedBlocks.foldIndexed(0) { index, acc, it ->
            acc + if (index == flattenedBlocks.size - 1) 0
            else flattenedBlocks.subList(index + 1, flattenedBlocks.size).filter { sit -> sit < it }.size
        }
        totalInversion % 2 == 0
    }

    /**
     * Returns the position of empty tail (0) in the form of (i,j)
     */
    val emptyTailPosition: Pair<Int, Int> by lazy {
        blocks.positionOf(0)
    }

    /**
     * Returns the dimension of the board.
     */
    val size: Int = blocks.size

    /**
     * Determines whether board is an instance of goal board.
     */
    fun isGoal(): Boolean = Arrays.deepEquals(blocks, GOAL_BLOCKS)

    /**
     * Validates blocks by checking blocks dimension and tail numbers.
     */
    private fun validateBlocks() {
        require(blocks.filter { it.size == 3 }.size == 3) {
            "Blocks must be in the form of 3X3 Matrix"
        }
        require(blocks.flatMap(IntArray::toList).containsAll((0..8).toList())) {
            "Board must contain all the tail(number)s in range of [0..8]"
        }
    }

    /**
     * Returns string representation of the board in the form of 3X3 matrix.
     */
    override fun toString(): String = blocks.toMatrixString()

    companion object {
        /**
         * Static instance of goal board blocks.
         */
        val GOAL_BLOCKS: Array<IntArray> = arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6),
            intArrayOf(7, 8, 0)
        )

        /**
         * Creates an instance of a goal board.
         * @param heuristicCalculator will be used to calculate heuristic.
         */
        @JvmStatic
        fun ofGoal(heuristicCalculator: HeuristicCalculator): PuzzleBoard =
            PuzzleBoard(GOAL_BLOCKS, heuristicCalculator)

    }
}

