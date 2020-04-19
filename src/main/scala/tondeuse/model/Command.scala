package tondeuse.model

case class Command(command:String) {
  require(command.forall(i => List('A', 'G', 'D').contains(i)), "Command must be either A, G or D")
}

