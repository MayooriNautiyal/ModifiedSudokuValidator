package gameMain

import scala.collection.mutable

/**
 * Class to represent the game board with constraints
 * @param board - N x N Array of Integer
 * @param constraintsMap - Set of Tuples of Pair of BoardCell
 * @param boardSize - N
 */
case class GameBoard(var board: Array[Array[Int]] = Array.ofDim[Int](0, 0),
                     var constraintsMap: mutable.Set[(BoardCell, BoardCell)] = mutable.Set.empty,
                     var boardSize: Int = 0)

/**
 * Class to represent a cell in board
 * @param row - row index of cell
 * @param col - column index of cell
 */
case class BoardCell(row: Int, col: Int)