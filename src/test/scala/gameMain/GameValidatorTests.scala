package gameMain

import org.mockito.Mockito.when
import org.scalatest.FunSuite
import org.scalatestplus.mockito.MockitoSugar

import scala.collection.mutable

class GameValidatorTests extends FunSuite with MockitoSugar {
  val mockedExampleBoard: InputBoardWithConstraints = mock[InputBoardWithConstraints]
  val testGameBoard = GameBoard(Array(Array(1,2), Array(2,1)), mutable.Set.empty, 2)

  def getGameValidatorObj(gameBoard: GameBoard):GameValidator = {
    when(mockedExampleBoard.getPuzzle).thenReturn(gameBoard)
    new GameValidator(mockedExampleBoard)
  }

  test(testName = "POSITIVE - validateCellValue method should return true if cell value is between 1 and boardsize"){
    val gameBoard = GameBoard(Array(Array(1,2), Array(2,1)), mutable.Set.empty, 2)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateCellValue(1,0)
    assertResult(expected = true)(result)
  }

  test(testName = "NEGATIVE - validateCellValue method should return false if cell value is not between 1 and boardsize"){
    val gameBoard = GameBoard(Array(Array(1,2), Array(2,8)), mutable.Set.empty, 2)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateCellValue(1,1)
    assertResult(expected = false)(result)
  }

  test(testName = "NEGATIVE - validateCellValue method should return false if cell value is negative"){
    val gameBoard = GameBoard(Array(Array(1,2), Array(-2,1)), mutable.Set.empty, 2)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateCellValue(1,0)
    assertResult(expected = false)(result)
  }

  test(testName = "NEGATIVE - validateCellValue method should return false if row is less than 0"){
    val gameValidator = getGameValidatorObj(testGameBoard)
    val result = gameValidator.validateCellValue(-2,1)
    assertResult(expected = false)(result)
  }

  test(testName = "NEGATIVE - validateCellValue method should return false if row is greater than boardsize - 1"){
    val gameValidator = getGameValidatorObj(testGameBoard)
    val result = gameValidator.validateCellValue(3,1)
    assertResult(expected = false)(result)
  }

  test(testName = "NEGATIVE - validateCellValue method should return false if col is less than 0"){
    val gameValidator = getGameValidatorObj(testGameBoard)
    val result = gameValidator.validateCellValue(1,-1)
    assertResult(expected = false)(result)
  }

  test(testName = "NEGATIVE - validateCellValue method should return false if col is greater than boardsize - 1"){
    val gameValidator = getGameValidatorObj(testGameBoard)
    val result = gameValidator.validateCellValue(1,2)
    assertResult(expected = false)(result)
  }

  test(testName = "POSITIVE - validateRow method should return true if current row has unique values between 1 and boardsize"){
    val gameBoard = GameBoard(Array(Array(1,3,2), Array(3,2,1), Array(2,1,3)), mutable.Set.empty, 3)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateRow(1)
    assertResult(expected = true)(result)
  }

  test(testName = "NEGATIVE - validateRow method should return false if current row has duplicate values"){
    val gameBoard = GameBoard(Array(Array(1,2,2), Array(3,2,1), Array(2,1,3)), mutable.Set.empty, 3)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateRow(0)
    assertResult(expected = false)(result)
  }

  test(testName = "NEGATIVE - validateRow method should return false if current row values are not in range [1, boardsize]"){
    val gameBoard = GameBoard(Array(Array(1,3,2), Array(3,2,1), Array(2,5,3)), mutable.Set.empty, 3)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateRow(2)
    assertResult(expected = false)(result)
  }

  test(testName = "POSITIVE - validateCol method should return true if current col has unique values between 1 and boardsize"){
    val gameBoard = GameBoard(Array(Array(1,3,2), Array(3,2,1), Array(2,1,3)), mutable.Set.empty, 3)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateColumn(1)
    assertResult(expected = true)(result)
  }

  test(testName = "NEGATIVE - validateCol method should return false if current col has duplicate values"){
    val gameBoard = GameBoard(Array(Array(1,3,2), Array(3,2,1), Array(3,1,3)), mutable.Set.empty, 3)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateColumn(0)
    assertResult(expected = false)(result)
  }

  test(testName = "NEGATIVE - validateCol method should return false if current col values are not in range [1, boardsize]"){
    val gameBoard = GameBoard(Array(Array(1,3,2), Array(3,2,5), Array(2,1,3)), mutable.Set.empty, 3)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateColumn(2)
    assertResult(expected = false)(result)
  }

  test(testName = "POSITIVE - validateConstraintOnCell method should return true if no constraints"){
    val gameBoard = GameBoard(Array(Array(1,3,2), Array(3,2,1), Array(2,1,3)), mutable.Set.empty, 3)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateConstraintOnCell
    assertResult(expected = true)(result)
  }

  test(testName = "POSITIVE - validateConstraintOnCell method should return true if given board satisfies all the contraints"){
    val gameBoard = GameBoard(
      Array(Array(1,3,2), Array(3,2,1), Array(2,1,3))
      ,mutable.Set((BoardCell(1,0), BoardCell(0,0)), (BoardCell(1,0), BoardCell(2,0)))
      ,3)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateConstraintOnCell
    assertResult(expected = true)(result)
  }

  test(testName = "NEGATIVE - validateConstraintOnCell method should return false if given board violates one of the contraints"){
    val gameBoard = GameBoard(
      Array(Array(1,3,2), Array(3,2,1), Array(2,1,3))
      ,mutable.Set((BoardCell(1,1), BoardCell(2,1)), (BoardCell(0,0), BoardCell(1,0)))
      ,3)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateConstraintOnCell
    assertResult(expected = false)(result)
  }

  test(testName = "POSITIVE - validateSolution method should return true if given board is a correct solution without any constraints"){
    val gameBoard = GameBoard(
      Array(Array(1,3,2), Array(3,2,1), Array(2,1,3))
      ,mutable.Set.empty
      ,3)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateSolution()
    assertResult(expected = true)(result)
  }

  test(testName = "POSITIVE - validateSolution method should return true if given board is a correct solution with constraints"){
    val gameBoard = GameBoard(
      Array(Array(1,3,2), Array(3,2,1), Array(2,1,3))
      ,mutable.Set((BoardCell(1,0), BoardCell(0,0)), (BoardCell(1,0), BoardCell(2,0)))
      ,3)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateSolution()
    assertResult(expected = true)(result)
  }

  test(testName = "NEGATIVE - validateSolution method should return false if given board invalid"){
    val gameBoard = GameBoard(
      Array(Array(1,-3,2), Array(10,2,1), Array(2,1,3))
      ,mutable.Set.empty
      ,3)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateSolution()
    assertResult(expected = false)(result)
  }

  test(testName = "NEGATIVE - validateSolution method should return false if given board doesn't satisfy the constraints"){
    val gameBoard = GameBoard(
      Array(Array(1,3,2), Array(3,2,1), Array(2,1,3))
      ,mutable.Set((BoardCell(1,1), BoardCell(2,1)), (BoardCell(0,0), BoardCell(1,0)))
      ,3)
    val gameValidator = getGameValidatorObj(gameBoard)
    val result = gameValidator.validateSolution()
    assertResult(expected = false)(result)
  }
}
