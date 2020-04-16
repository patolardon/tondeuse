import org.scalatest.FlatSpec
import plouandre.{Garden, Mower, North, Position, West}

class MowerTests extends FlatSpec {
  "A turn" should "return a mower with a new orientation of position if it is G or D" in {
    val position = Position(1, 2, North)
    val garden = Garden(5, 5)
    val mower = Mower(position, garden)
    assert(mower.turn('G').position === Position(1, 2, West))
    intercept[IllegalArgumentException] {
      mower.turn('F')
    }

  }

  "An advance" should "increase abscisse or ordonnee according to orientation" in {
    val position = Position(1, 2, North)
    val garden = Garden(5, 5)
    val mower = Mower(position, garden)
    assert(mower.advance.position === Position(1, 3, North))
  }

  "An advance" should "return Illegal Argument exception if going out of the garden" in {
    val position = Position(1, 5, North)
    val garden = Garden(5, 5)
    val mower = Mower(position, garden)
    intercept[IllegalArgumentException] {
      mower.advance
    }
  }

  "Run" should "Run all command in a string of command" in {
    val position = Position(1, 1, North)
    val garden = Garden(5, 5)
    val mower = Mower(position, garden)
    assert(mower.run("AAG").position === Position(1, 3, West))
    intercept[IllegalArgumentException] {
      mower.run("AAGF")
    }
  }

}
