import org.scalatest.FlatSpec
import tondeuse.Parser.{North, Parser}
import tondeuse.model.{Command, Garden, Position}
import tondeuse.model

class ParserTest extends FlatSpec {
  "Parse File" should "return a tuple of the garden and a tuple (position, movements)" in {
    val inputFile = List("AAA", "1 2 N", "5 5")
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

  "Parse Command" should "return a command if the command composed of A, D or G" in {
    val inputCommand = "AAG"
    assert(Parser.parseCommand(inputCommand) === Command("AAG"))
  }

  "Parse Command" should "return a command comprised only of A, D or G even if it has other commands" in {
    val inputCommand = "AAGF"
    assert(Parser.parseCommand(inputCommand) === Command("AAG"))
  }

  "Parse Command" should "return an empty command if it has no A, D or G" in {
    val inputCommand = "HJF"
    assert(Parser.parseCommand(inputCommand) === Command(""))
  }
}