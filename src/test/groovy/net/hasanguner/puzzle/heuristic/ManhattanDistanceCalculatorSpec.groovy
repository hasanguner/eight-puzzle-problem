package net.hasanguner.puzzle.heuristic

import spock.lang.Specification
import spock.lang.Unroll

class ManhattanDistanceCalculatorSpec extends Specification {

    def heuristicCalculator = new ManhattanDistanceCalculator()

    @Unroll
    def "manhattan distance between #current and #goal must be equal to #cost"() {
        expect:
        heuristicCalculator.calculate(current as int[][], goal as int[][]) == cost
        where:
        current                           | goal                              || cost
        [[0, 1, 2], [3, 4, 5], [6, 7, 8]] | [[1, 2, 3], [4, 5, 6], [7, 8, 0]] || 16
        [[1, 2, 3], [4, 0, 5], [6, 7, 8]] | [[1, 2, 3], [4, 5, 6], [7, 8, 0]] || 8
        [[1, 2, 3], [4, 5, 6], [7, 8, 0]] | [[1, 2, 3], [4, 5, 6], [7, 8, 0]] || 0
    }

}
