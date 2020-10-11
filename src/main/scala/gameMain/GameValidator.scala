package gameMain

import scala.collection.mutable

/**
 * Class Validates the input board using the given constraints
 * @param board contains input board, constraints and boardSize
 */
class GameValidator(board: InputBoardWithConstraints) {
  private var gameBoard:GameBoard = board.getPuzzle

  /**
   * Method validates the board, returns true if solution is correct else false
   * @return Boolean
   */
  def validateSolution(): Boolean = {
    for (index <- 0 until gameBoard.boardSize) {
      if (!validateRow(index) || !validateColumn(index))
        return false
    }
    if (!validateConstraintOnCell)
      return false
    true
  }

  /**
   * Method validates the input row of the board.
   * Returns true is all values in row are unique and in the interval [0, boardSize-1] else false
   * @param row - index of current row to validate
   * @return Boolean
   */
  def validateRow(row: Int): Boolean = {
    val solvedBoard = gameBoard.board
    var traversedSet = mutable.Set[Int]()
    for (index <- 0 until gameBoard.boardSize) {
      if (!validateCellValue(row, index) || traversedSet.contains(solvedBoard(row)(index)))
        return false
      traversedSet += solvedBoard(row)(index)
    }
    true
  }

  /**
   * Method validates the input column of the board.
   * Returns true is all values in column are unique and in the interval [0, boardSize-1] else false
   * @param col - index of current column to validate
   * @return Boolean
   */
  def validateColumn(col: Int): Boolean = {
    val solvedBoard = gameBoard.board
    var traversedSet = mutable.Set[Int]()
    for (index <- 0 until gameBoard.boardSize) {
      if (!validateCellValue(index, col) || traversedSet.contains(solvedBoard(index)(col)))
        return false
      traversedSet += solvedBoard(index)(col)
    }
    true
  }

  /**
   * Method validates a cell value.
   * Returns true if row index and column index are valid and cell(row,col) is in range [1, boardSize] else false
   * @param row - row index of current cell to validate
   * @param col - column index of current cell to validate
   * @return Boolean
   */
  def validateCellValue(row: Int, col: Int): Boolean ={
    val solvedBoard = gameBoard.board
    if ((row < 0 || row > gameBoard.boardSize-1) || (col < 0 || col > gameBoard.boardSize-1))
      return false
    if (solvedBoard(row)(col) < 1 || solvedBoard(row)(col) > gameBoard.boardSize)
      return false
    true
  }

  /**
   * Method validates if the board satisfies all the given constraints
   * Returns true if there are no contraints or all constraints are satisfied by the board else false
   * @return Boolean
   */
  def validateConstraintOnCell: Boolean = {
    val constraintMap = gameBoard.constraintsMap
    val solvedBoard = gameBoard.board
    constraintMap.foreach(cellPair => {
      val aRow = cellPair._1.row
      val aCol = cellPair._1.col
      val bRow = cellPair._2.row
      val bCol = cellPair._2.col
      if (solvedBoard(aRow)(aCol) < solvedBoard(bRow)(bCol))
        return false
    })
    true
  }
}
