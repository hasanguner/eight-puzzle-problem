package net.hasanguner.puzzle

import net.hasanguner.search.Search
import net.hasanguner.search.SearchResult

class AIPuzzleSolver(
    search: Search,
    board: PuzzleBoard
) : PuzzleSolver(board) {

    private val searchResult: SearchResult by lazy {
        search.search(board, PuzzleBoard.ofGoal(board.heuristicCalculator))
    }

    /**
     * Particular implementation of [PuzzleSolver.moves].
     * @return the number of moves used to solve the 8-puzzle problem.
     */
    override fun moves(): Int =
        searchResult.path.size - 1

    /**
     * Particular implementation of [PuzzleSolver.solution].
     * @return the shortest board sequence to the solution.
     */
    @Suppress("UNCHECKED_CAST")
    override fun solution(): Collection<PuzzleBoard> =
        searchResult.path as Collection<PuzzleBoard>


}