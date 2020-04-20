package tondeuse.Parser

import tondeuse.model
import tondeuse.model.{Garden, Position}

object Parser {
  def parseFile(inputFile: List[String]): (String, (String, String)) = {
    val movements = inputFile.head
    val initialPositions = inputFile(1)
    val sizeGarden = inputFile(2)
    (sizeGarden, (initialPositions, movements))
  }

  def parseGarden(gardenSize: String): Garden = {
    val garden = gardenSize.split(" ").toSeq
    (garden(0) matches """\d+""", garden(1) matches """\d+""") match {
      case (true, true) =>  Garden(garden(0).toInt, garden(1).toInt)
      case (true, false) => throw new IllegalArgumentException(s"bad abscisse for garden ${garden(0)}" )
      case (false, true) => throw new IllegalArgumentException(s"bad ordonnee for garden ${garden(1)}" )
      case _ => throw new IllegalArgumentException(s"bad abscisse and ordonnee for garden ${garden(0)} and ${garden(1)}" )
    }
  }

  def parsePosition(position: String): Position = {
    val positionSeq = position.split(" ").toSeq
    (positionSeq.head.toIntOption, positionSeq.tail.head.toIntOption,
      OrientationParser.parse(positionSeq(2))) match {
      case (Some(abscisse), Some(ordonnee), Some(orientation)) =>  model.Position(abscisse, ordonnee, orientation)
      case (None, _, _) => throw new IllegalArgumentException(s"bad abscisse for position ${positionSeq(0)} must be an Int" )
      case (_, None, _) => throw new IllegalArgumentException(s"bad ordonnee for position ${positionSeq(1)} must be an Int" )
      case (_, _, None) => throw new IllegalArgumentException(s"bad orientation for position ${positionSeq(2)} must be N, O, E or S" )
    }
  }

  def unparsePosition(position: Position): String = {
    List(position.abscisse.toString, position.ordonnee.toString, position.orientation.associatedString).mkString(" ")
  }

}
