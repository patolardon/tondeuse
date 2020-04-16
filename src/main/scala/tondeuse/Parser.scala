package tondeuse

object Parser {
  def parseFile(inputFile: List[String]): (String, List[(String, String)]) = {
    val sizeGarden = inputFile(0)
    val initialPositions = inputFile.zipWithIndex.filter(x => (x._2 % 2 == 1) && (x._2 != 0)).map(_._1)
    val movements = inputFile.zipWithIndex.filter(x => (x._2 % 2 == 0) && (x._2 != 0)).map(_._1)
    if (movements.length != initialPositions.length) {
      System.err.println("[warning] " + s"inital position ${initialPositions.last} has no associated movements")
    }
    (sizeGarden, initialPositions.zip(movements))
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
      case (Some(abscisse), Some(ordonnee), Some(orientation)) =>  Position(abscisse, ordonnee, orientation)
      case (None, _, _) => throw new IllegalArgumentException(s"bad abscisse for position ${positionSeq(0)} must be an Int" )
      case (_, None, _) => throw new IllegalArgumentException(s"bad ordonnee for position ${positionSeq(1)} must be an Int" )
      case (_, _, None) => throw new IllegalArgumentException(s"bad orientation for position ${positionSeq(2)} must be N, O, E or S" )
    }
  }

  def unparsePosition(position: Position): String = {
    List(position.abscisse.toString, position.ordonnee.toString, position.orientation.associatedString).mkString(" ")
  }

}
