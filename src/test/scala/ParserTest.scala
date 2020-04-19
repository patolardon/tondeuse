import org.scalatest.FlatSpec
import tondeuse.Parser.{North, Parser}
import tondeuse.model.{Garden, Position}
import tondeuse.model

class ParserTest extends FlatSpec {
  "Parse File" should "return a tuple of the garden and a tuple (position, movements)" in {
    val inputFile = List("5 5", "1 2 N", "AAA")
    val fileParsed = Parser.parseFile(inputFile)
    assert(fileParsed === ("5 5", ("1 2 N", "AAA")))

  }
  "Parse Garden" should "return an error as right if there is and odd number of tuple (position, movements)" in {
    val inputGarden = "5 A"
    intercept[IllegalArgumentException] {
      Parser.parseGarden(inputGarden)
    }
  }
  "Parse Garden" should "return a garden as right if there is and even number of tuple (position, movements)" in {
    val inputGarden = "5 5"
    assert(Parser.parseGarden(inputGarden) === Garden(5, 5))
  }
  "Parse Position" should "return a position if it is of format Int Int Char (Char in N O E S)" in {
    val inputPosition = "1 1 N"
    assert(Parser.parsePosition(inputPosition) === Position(1, 1, North))
  }

  "Parse Position" should "return an error if it is not of format Int Int Char (Char in N O E S)" in {
    val inputPosition = "1 1 C"
    intercept[IllegalArgumentException] {
      Parser.parsePosition(inputPosition)
    }
  }
  "Unparse Position" should "return an string a position from a position" in {
    val inputPosition = model.Position(1, 1, North)
    assert(Parser.unparsePosition(inputPosition) === "1 1 N")
  }
}