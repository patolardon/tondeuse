import scala.util.{Failure, Success, Try}
import java.nio.file.Files
import java.nio.file.Paths
import scala.util.Using

import plouandre.{Mower, Parser}
import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    val inputFile = Using(Source.fromFile(args(0))) {source => source.getLines().toList} match {
        case Success(file) => file
        case Failure(error) => throw new IllegalArgumentException(s"Need a valid input file ${error.getMessage}")
      }
    val input = Parser.parseFile(inputFile)
    val garden = Parser.parseGarden(input._1)
    val finalPositions = for (movements <- input._2) yield {
      val position = Parser.parsePosition(movements._1)
      val commands = movements._2
      val maTondeuse = Mower(position, garden)
      Parser.unparsePosition(maTondeuse.run(commands).position)
    }
    val content = finalPositions.mkString("\n").getBytes
    Files.write(Paths.get("src/results/out.txt"), content)
  }
}
