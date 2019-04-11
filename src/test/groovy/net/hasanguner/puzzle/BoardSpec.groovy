package net.hasanguner.puzzle

import net.hasanguner.puzzle.heuristic.HeuristicCalculator
import spock.lang.Specification
import spock.lang.Unroll

class BoardSpec extends Specification {

    def "board should not be constructed if blocks are missing"() {
        given:
        int[][] blocks = [[1, 2, 3], [4, 5, 6], [7, 8]]
        def calculator = Mock(HeuristicCalculator)
        when:
        new PuzzleBoard(blocks, calculator)
        then:
        def ex = thrown IllegalArgumentException
        println "MESSAGE : ${ex.getMessage()}"
    }

    def "board should not be constructed if blocks are not in form of 3X3"() {
        given:
        int[][] blocks = [[1, 2, 3, 4], [5, 6, 7], [8, 0]]
        def calculator = Mock(HeuristicCalculator)
        when:
        new PuzzleBoard(blocks, calculator)
        then:
        def ex = thrown IllegalArgumentException
        println "MESSAGE : ${ex.getMessage()}"
    }

    def "board should not be constructed if tail numbers not in range of [0..8]"() {
        given:
        int[][] blocks = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
        def calculator = Mock(HeuristicCalculator)
        when:
        new PuzzleBoard(blocks, calculator)
        then:
        def ex = thrown IllegalArgumentException
        println "MESSAGE : ${ex.getMessage()}"
    }

    def "board should be constructed with the deep copy of blocks"() {
        given:
        int[][] blocks = [[1, 2, 3], [4, 5, 6], [7, 8, 0]]
        def calculator = Mock(HeuristicCalculator)
        when:
        def board = new PuzzleBoard(blocks, calculator)
        blocks[0][0] = 2
        blocks[0][1] = 1
        then:
        board.blocks() != blocks
    }

    def "blocks() should return a deep copy of the blocks"() {
        given:
        int[][] blocks = [[1, 2, 3], [4, 0, 5], [6, 7, 8]]
        def calculator = Mock(HeuristicCalculator)
        def board = new PuzzleBoard(blocks, calculator)
        when:
        int[][] copyBlocks = board.blocks()
        copyBlocks[0][0] = 2
        copyBlocks[0][1] = 1
        then:
        board.blocks() != copyBlocks
    }

    def "board should use heuristic calculator to calculate heuristic"() {
        given:
        int[][] blocks = [[1, 2, 3], [4, 0, 5], [6, 7, 8]]
        def calculator = Mock(HeuristicCalculator)
        def board = new PuzzleBoard(blocks, calculator)
        when:
        def heuristic = board.heuristic
        then:
        1 * calculator.calculate(blocks, PuzzleBoard.GOAL_BLOCKS) >> 8
        heuristic == 8
    }

    def "board should return all adjacencies with 0 path cost"() {
        given:
        int[][] blocks = [[0, 1, 2], [3, 4, 5], [6, 7, 8]]
        def calculator = Mock(HeuristicCalculator)
        def board = new PuzzleBoard(blocks, calculator)
        when:
        def adjacencies = board.adjacencies
        then:
        adjacencies.size() == 2
        adjacencies.every { it.cost == 0 }
    }

    @Unroll
    def "board #blocks should return #neighbors.size() number of neighbors"() {
        expect:
        def board = new PuzzleBoard(blocks as int[][], Mock(HeuristicCalculator))
        board.solvable
        println "NEIGHBORS : ${board.neighbors.collect { "\n$it\n" }}"
        board.neighbors.every { it.solvable }
        def flatNeighbors = board.neighbors*.blocks().flatten()
        neighbors.flatten().containsAll(flatNeighbors)
        where:
        blocks                            || neighbors
        [[0, 1, 2], [3, 4, 5], [6, 7, 8]] || [[[3, 1, 2], [0, 4, 5], [6, 7, 8]],
                                              [[1, 0, 2], [3, 4, 5], [6, 7, 8]]]

        [[1, 0, 2], [3, 4, 5], [6, 7, 8]] || [[[0, 1, 2], [3, 4, 5], [6, 7, 8]],
                                              [[1, 4, 2], [3, 0, 5], [6, 7, 8]],
                                              [[1, 2, 0], [3, 4, 5], [6, 7, 8]]]

        [[1, 2, 0], [3, 4, 5], [6, 7, 8]] || [[[1, 2, 5], [3, 4, 0], [6, 7, 8]],
                                              [[1, 0, 2], [3, 4, 5], [6, 7, 8]]]

        [[1, 2, 5], [3, 4, 0], [6, 7, 8]] || [[[1, 2, 0], [3, 4, 5], [6, 7, 8]],
                                              [[1, 2, 5], [3, 4, 8], [6, 7, 0]],
                                              [[1, 2, 5], [3, 0, 4], [6, 7, 8]]]

        [[1, 2, 5], [3, 4, 8], [6, 7, 0]] || [[[1, 2, 5], [3, 4, 0], [6, 7, 8]],
                                              [[1, 2, 5], [3, 4, 8], [6, 0, 7]]]

        [[1, 2, 5], [3, 4, 8], [6, 0, 7]] || [[[1, 2, 5], [3, 0, 8], [6, 4, 7]],
                                              [[1, 2, 5], [3, 4, 8], [0, 6, 7]],
                                              [[1, 2, 5], [3, 4, 8], [6, 7, 0]]]

        [[1, 2, 5], [3, 4, 8], [0, 6, 7]] || [[[1, 2, 5], [0, 4, 8], [3, 6, 7]],
                                              [[1, 2, 5], [3, 4, 8], [6, 0, 7]]]

        [[1, 2, 5], [0, 4, 8], [3, 6, 7]] || [[[0, 2, 5], [1, 4, 8], [3, 6, 7]],
                                              [[1, 2, 5], [3, 4, 8], [0, 6, 7]],
                                              [[1, 2, 5], [4, 0, 8], [3, 6, 7]]]

        [[1, 2, 5], [4, 0, 8], [3, 6, 7]] || [[[1, 0, 5], [4, 2, 8], [3, 6, 7]],
                                              [[1, 2, 5], [4, 6, 8], [3, 0, 7]],
                                              [[1, 2, 5], [0, 4, 8], [3, 6, 7]],
                                              [[1, 2, 5], [4, 8, 0], [3, 6, 7]]]

    }

    @Unroll
    def "board #blocks should return solvable #solvable"() {
        expect:
        def board = new PuzzleBoard(blocks as int[][], Mock(HeuristicCalculator))
        board.solvable == solvable
        where:
        blocks                            || solvable
        [[0, 1, 2], [3, 4, 5], [6, 7, 8]] || true
        [[0, 1, 3], [4, 2, 5], [7, 8, 6]] || true
        [[5, 2, 8], [4, 1, 7], [0, 3, 6]] || true
        [[1, 2, 3], [4, 5, 6], [7, 8, 0]] || true
        [[8, 1, 2], [0, 4, 3], [7, 6, 5]] || false
    }

    @Unroll
    def "board #blocks should return empty tail index as (#i,#j)"() {
        expect:
        def board = new PuzzleBoard(blocks as int[][], Mock(HeuristicCalculator))
        def position = board.emptyTailPosition
        position.first == i
        position.second == j
        where:
        blocks                            || i || j
        [[0, 1, 2], [3, 4, 5], [6, 7, 8]] || 0 || 0
        [[1, 0, 2], [3, 4, 5], [6, 7, 8]] || 0 || 1
        [[1, 2, 0], [3, 4, 5], [6, 7, 8]] || 0 || 2
        [[1, 2, 5], [3, 4, 0], [6, 7, 8]] || 1 || 2
        [[1, 2, 5], [3, 4, 8], [6, 7, 0]] || 2 || 2
        [[1, 2, 5], [3, 4, 8], [6, 0, 7]] || 2 || 1
        [[1, 2, 5], [3, 4, 8], [0, 6, 7]] || 2 || 0
        [[1, 2, 5], [0, 4, 8], [3, 6, 7]] || 1 || 0
        [[1, 2, 5], [4, 0, 8], [3, 6, 7]] || 1 || 1
    }

    def "board dimension size must be equal to 3"() {
        given:
        int[][] blocks = [[0, 1, 2], [3, 4, 5], [6, 7, 8]]
        def board = new PuzzleBoard(blocks, Mock(HeuristicCalculator))
        when:
        def size = board.size
        then:
        3 == size
    }

    @Unroll
    def "board #blocks is an instance of a goal board : #isGoal"() {
        expect:
        def board = new PuzzleBoard(blocks as int[][], Mock(HeuristicCalculator))
        board.isGoal() == isGoal
        where:
        blocks                            || isGoal
        [[1, 2, 3], [4, 5, 6], [7, 8, 0]] || true
        [[0, 1, 2], [3, 4, 5], [6, 7, 8]] || false
        [[1, 0, 2], [3, 4, 5], [6, 7, 8]] || false
        [[1, 2, 0], [3, 4, 5], [6, 7, 8]] || false
    }

}
