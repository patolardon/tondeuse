package tondeuse.model

import tondeuse.Parser.{East, North, South, West}
import tondeuse._

case class Mower(position: Position, garden: Garden) {
  require(position.isInGarden(garden), "Mower has left the garden")

  def turn(command: Char): Mower = {
    val turnLeft = Map(North -> West, West -> South, South -> East, East -> North)
    val turnRight = turnLeft.map(_.swap)

    val newPosition = command match {
      case 'G' => model.Position(position.abscisse, position.ordonnee, turnLeft(position.orientation))
      case 'D' => model.Position(position.abscisse, position.ordonnee, turnRight(position.orientation))
      case badTurn =>  throw new IllegalArgumentException(s"Turn is not possible : $badTurn")
    }

    Mower(newPosition, garden)
  }

  def advance: Mower = {
    val orientation = position.orientation
    val newOrientation = orientation match {
      case North => Position( position.abscisse, position.ordonnee + 1, position.orientation)
      case South => Position(position.abscisse, position.ordonnee - 1, position.orientation)
      case East => Position(position.abscisse + 1, position.ordonnee, position.orientation)
      case West => Position(position.abscisse - 1, position.ordonnee, position.orientation)
    }

    val newPosition = newOrientation

    Mower(newPosition, garden)
  }

  def run(commandes: Command): Mower = {
    commandes.command.foldLeft(this) {
      case (mower, 'A') => mower.advance
      case (mower, 'G') => mower.turn('G')
      case (mower, 'D') => mower.turn('D')
      case (_, c) => throw new IllegalArgumentException(s"wrong command : $c")
    }
  }

}
