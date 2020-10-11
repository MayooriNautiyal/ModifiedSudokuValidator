package gameMain

import scala.collection.mutable

/**
 * Class to prepare and get a sample board with sample constraints
 * This class is responsible for populating the GameBoard class object with sample board and its constraints.
 */
class InputBoardWithConstraints {

  /**
   * Method to get a complete puzzle with board and its constraints
   * @return GameBoard object
   */
  def getPuzzle: GameBoard ={
    val gameBoard = GameBoard()
    gameBoard.board = getBoard
    gameBoard.constraintsMap = getConstraints
    gameBoard.boardSize = gameBoard.board.length
    gameBoard
  }

  /**
   * Method to get a NxN board. Board index starting with zero incrementing row-wise.
   * Board values starting with 1.
   * @return N x N matrix
   */
  private def getBoard: Array[Array[Int]] = {
    val board = Array(
      Array(2, 1, 4, 3),
      Array(1, 4, 3, 2),
      Array(3, 2, 1, 4),
      Array(4, 3, 2, 1)
    )
    board
  }

  /**
   * Method to get board constraints. Board index starting with zero incrementing row-wise.
   * Constraints are represented as Set of Tuples of Pair of BoardCell.
   * Pair(cellA, cellB) implies cellA > cellB
   * @return Set(Tuples(BoardCell, BoardCell))
   */
  private def getConstraints: mutable.Set[(BoardCell, BoardCell)] = {
    var constraintMap = mutable.Set[(BoardCell, BoardCell)]()
    constraintMap += ((BoardCell(0,0), BoardCell(0,1)))
    constraintMap += ((BoardCell(1,2), BoardCell(2,2)))
    constraintMap += ((BoardCell(3,0), BoardCell(3,1)))
    constraintMap += ((BoardCell(3,1), BoardCell(2,1)))
    constraintMap
  }
}
