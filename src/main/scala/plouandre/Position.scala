package plouandre

case class Position(abscisse: Int, ordonnee:Int, orientation:Orientation) {

  def isInGarden(garden:Garden):Either[Position, IllegalArgumentException] = {
    if ((abscisse<0) || (abscisse>garden.maxAbscisse) || (ordonnee>garden.maxOrdonnee) || (ordonnee<0)) {
      Right(new IllegalArgumentException(s"sortie de jardin"))
    }
    else {
      Left(Position(abscisse, ordonnee, orientation))
    }
  }
}
