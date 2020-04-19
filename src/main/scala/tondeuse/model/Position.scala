package tondeuse.model

import tondeuse.Parser.Orientation

case class Position(abscisse: Int, ordonnee:Int, orientation:Orientation) {
  def isInGarden(garden:Garden):Boolean = {
    !((abscisse<0) || (abscisse>garden.maxAbscisse) || (ordonnee>garden.maxOrdonnee) || (ordonnee<0))
  }
}
