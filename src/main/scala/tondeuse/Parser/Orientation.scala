package tondeuse.Parser

sealed trait Orientation extends Product with Serializable {
  val associatedString: String
}

case object North extends Orientation {
  val associatedString = "N"
}
case object East extends Orientation{
  val associatedString = "E"
}
case object West extends Orientation{
  val associatedString = "O"
}
case object South extends Orientation{
  val associatedString = "S"
}

object OrientationParser {
  def parse(input:String): Option[Orientation] = input match {
    case "N" => Some(North)
    case "E" => Some(East)
    case "O" => Some(West)
    case "S" => Some(South)
    case _ => None
  }
}
