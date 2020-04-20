import org.scalatest.FlatSpec
import Main.run

class MainTests extends FlatSpec {
  "A File" should "return a new position if it is correct" in {
    val file = "AAD\n1 1 N\n7 7"
    assert(Main.run(file) == "1 3 E")
  }

  "A File" should  "return the same position if no command is given" in {
    val file = "\n1 1 N\n7 7"
    assert(Main.run(file) == "1 1 N")
  }

  "A File" should  "return the same position if an order to get out of the garden is given" in {
    val file = "AA\n1 1 N\n1 1"
    assert(Main.run(file) == "1 1 N")
  }

  "A File" should  "only read good commands if some bad ones are given" in {
    val file = "AADF\n1 1 N\n7 7"
    assert(Main.run(file) == "1 3 E")
  }

  "A File" should  "throw an exception if the initial coordinate is not normal" in {
    val file = "AAD\n1 1\n7 7"
    intercept[IllegalArgumentException] {
      Main.run(file)
    }
  }

  "A File" should  "throw an exception if the garden is not normal" in {
    val file = "AAD\n1 1 N\nA 8"
    intercept[IllegalArgumentException] {
      Main.run(file)
    }
  }


}
