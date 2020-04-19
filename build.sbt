name := "tondeuse"

version := "0.1"

scalaVersion := "2.13.1"
libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.13" % "3.0.8" % "test",
  "org.apache.kafka" %% "kafka-streams-scala" % "2.4.1"
)