package plouandre

case class Mower(position: Position, garden: Garden) {
  private val positionInGarden: Position = position.isInGarden(garden) match {
    case Left(position) => position
    case Right(error) => throw error
  }

  def turn(command: Char): Mower = {
    val turnLeft = Map(North -> West, West -> South, South -> East, East -> North)
    val turnRight = turnLeft.map(_.swap)

    val newPosition = command match {
      case 'G' => Position(positionInGarden.abscisse, positionInGarden.ordonnee, turnLeft(positionInGarden.orientation))
      case 'D' => Position(positionInGarden.abscisse, positionInGarden.ordonnee, turnRight(positionInGarden.orientation))
      case badTurn =>  throw new IllegalArgumentException(s"Turn is not possible : $badTurn")
    }

    Mower(newPosition, garden)
  }

  def advance: Mower = {
    val orientation = position.orientation
    val newOrientation = orientation match {
      case North => Position(position.abscisse, position.ordonnee + 1, position.orientation).isInGarden(garden)
      case South => Position(position.abscisse, position.ordonnee - 1, position.orientation).isInGarden(garden)
      case East => Position(position.abscisse + 1, position.ordonnee, position.orientation).isInGarden(garden)
      case West => Position(position.abscisse - 1, position.ordonnee, position.orientation).isInGarden(garden)
    }

    val newPosition = newOrientation match {
      case Left(orient) => orient
      case Right(orient) => throw orient
    }

    Mower(newPosition, garden)
  }

  def run(commandes: String): Mower = {

    commandes.foldLeft(this) {
      case (mower, 'A') => mower.advance
      case (mower, 'G') => mower.turn('G')
      case (mower, 'D') => mower.turn('D')
      case (mower, c) => throw new IllegalArgumentException(s"wrong command : $c")
    }
  }

}
