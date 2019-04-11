package net.hasanguner.puzzle

object Matrix {

    fun Array<IntArray>.swap(oldI: Int, oldJ: Int, newI: Int, newJ: Int): Array<IntArray> {
        val newBlocks = map { it.copyOf() }.toTypedArray()
        val temp = newBlocks[oldI][oldJ]
        newBlocks[oldI][oldJ] = newBlocks[newI][newJ]
        newBlocks[newI][newJ] = temp
        return newBlocks
    }

    fun Array<IntArray>.positionOf(value: Int): Pair<Int, Int> =
        flatMap(IntArray::toList)
            .indexOf(value)
            .let { it / size to it % size }

    fun Array<IntArray>.toMatrixString(): String =
        joinToString("\n") { it.joinToString(" ") }

    fun Array<IntArray>.toListString(): String =
        toList().map { it.toList() }.toString()

    fun Array<IntArray>.toFlatString(): String =
        flatMap(IntArray::toList).joinToString("", transform = Int::toString)

}