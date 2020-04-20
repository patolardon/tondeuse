package tondeuse.model

import tondeuse.Parser.{East, North, South, West}
import tondeuse._
import com.typesafe.scalalogging.Logger

import scala.util.Try

case class Mower(position: Position, garden: Garden) {
  require(position.isInGarden(garden), "Mower has left the garden")
  val logger = Logger("Mower")
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

  def advance: Either[IllegalArgumentException, Mower] = {
    val orientation = position.orientation
    val newOrientation = orientation match {
      case North => Position( position.abscisse, position.ordonnee + 1, position.orientation)
      case South => Position(position.abscisse, position.ordonnee - 1, position.orientation)
      case East => Position(position.abscisse + 1, position.ordonnee, position.orientation)
      case West => Position(position.abscisse - 1, position.ordonnee, position.orientation)
    }

    val newPosition = newOrientation

    Try(Mower(newPosition, garden)).map(Right(_)).getOrElse(Left(new IllegalArgumentException))
  }

  def run(commandes: Command): Mower = {
    commandes.command.foldLeft(this) {
      case (mower, 'A') => mower.advance match {
        case Right(value) => value
        case Left(_) => {
        logger.info("mower cannot advance, otherwise it would leave the garden. Cancelling last move.")
          mower
        }
      }
      case (mower, turnCommand) => mower.turn(turnCommand)
    }
  }

}
