package gameMain

/**
 * Entry point for the Game App
 * Prints true if the board is a valid board and satisfies all constraints else false
 */
object Solution extends App {
  val exampleBoard = new InputBoardWithConstraints()
  val validator = new GameValidator(exampleBoard)
  var result = validator.validateSolution()

  println("Result: " + result)
}
