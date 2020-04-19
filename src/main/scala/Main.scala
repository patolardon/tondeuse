import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.serialization.Serdes.StringSerde
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.kstream.{KStream, KTable}
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.Serdes._
import tondeuse.Parser.Parser
import tondeuse.model.{Command, Mower}


object Main extends App {

  // create config
  val config: Properties = {
    val properties = new Properties()
    properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "MowerStream")
    val bootstrapServers = if (args.length > 0) args(0) else "localhost:9092"
    properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, classOf[StringSerde].getName)
    properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, classOf[StringSerde].getName)
    properties
  }

  // create topology
  val streamsBuilder = new StreamsBuilder

  //input topics
  val inititalPositionTopic: KTable[String, String] = streamsBuilder.table[String, String]("mower_initial_position")
  val gardenTopic: KTable[String, String] = streamsBuilder.table[String, String]("mower_garden_size")
  val commandTopic: KTable[String, String] = streamsBuilder.table[String, String]("mower_commands")


  val joinedStream:KStream[String, String] = gardenTopic
    .join(inititalPositionTopic)((l:String, r:String) => {
    s"${l}\n${r}"
  })
    .join(commandTopic)((l:String, r:String) => {
    s"${l}\n${r}"
  })
    .mapValues(value => run(value))
    .toStream

  joinedStream.to("mower_positions")(Produced.`with`(Serdes.String(), Serdes.String()))

  // build the topology
  val kafkaStreams = new KafkaStreams(streamsBuilder.build(), config)

  //start the stream
  kafkaStreams.start()

  def run(inputFile:String):String = {
    val input = Parser.parseFile(inputFile.split("\n").toList)
    val garden = Parser.parseGarden(input._1)
    val movements = input._2
    val position = Parser.parsePosition(movements._1)
    val commands = Command(movements._2)
    val maTondeuse = Mower(position, garden)
    Parser.unparsePosition(maTondeuse.run(commands).position)
  }

}
