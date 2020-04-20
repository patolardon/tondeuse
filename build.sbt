name := "tondeuse"

version := "0.1"

scalaVersion := "2.13.1"
libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.13" % "3.0.8" % "test",
  "org.apache.kafka" %% "kafka-streams-scala" % "2.4.1",
  "org.slf4j" % "slf4j-simple" % "1.8.0-alpha2",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
)