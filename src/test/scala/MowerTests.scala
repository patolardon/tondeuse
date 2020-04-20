import org.scalatest.FlatSpec
import tondeuse.Parser.{North, West}
import tondeuse.model.{Command, Garden, Mower, Position}
import tondeuse.model

class MowerTests extends FlatSpec {
  "A turn" should "return a mower with a new orientation of position if it is G or D" in {
    val position = Position(1, 2, North)
    val garden = Garden(5, 5)
    val mower = Mower(position, garden)
    assert(mower.turn('G').position === model.Position(1, 2, West))
    intercept[IllegalArgumentException] {
      mower.turn('F')
    }

  }

  "An advance" should "increase abscisse or ordonnee according to orientation" in {
    val position = model.Position(1, 2, North)
    val garden = Garden(5, 5)
    val mower = Mower(position, garden)
    assert(mower.advance.getOrElse(mower).position === model.Position(1, 3, North))
  }

  "An advance" should "return Illegal Argument exception if going out of the garden" in {
    val position = model.Position(1, 5, North)
    val garden = Garden(5, 5)
    val mower = Mower(position, garden)
    intercept[IllegalArgumentException] {
      mower.advance match {
        case Right(value) => value
        case Left(error) => throw error
      }
    }
  }

  "Run" should "Run all command in a string of command" in {
    val position = model.Position(1, 1, North)
    val garden = Garden(5, 5)
    val mower = Mower(position, garden)
    assert(mower.run(Command("AAG")).position === model.Position(1, 3, West))
    intercept[IllegalArgumentException] {
      mower.run(Command("AAGF"))
    }
  }

}
