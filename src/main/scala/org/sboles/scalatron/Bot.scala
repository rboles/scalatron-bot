
package org.sboles.scalatron

/**
 * Parses the command and reacts
 */
class Bot {

  def respond(input: String): String = {
    val (opcode, params) = CommandParser(input)

    opcode match {
      case "Welcome" =>
        welcome(
          params("name"),
          params("path"),
          params("apocalypse").toInt,
          params("round").toInt
        )
      case "React" => react(params("generation").toInt, params)
      case "Goodbye" => ""
      case _ => ""
    }
  }

  def welcome(name: String, path: String, apocalypse: Int, round: Int) = ""

  def react(generation: Int, params: Map[String, String]) = {
    if ( generation == 0 ) Master.react(params)
    else Slave.react(params)
  }
}

object CommandParser {

  def apply(cmd: String): (String, Map[String, String]) = {

    def splitParam(param: String): (String, String) = {
      val segments = param.split('=')
      if ( segments.length != 2 )
        throw new IllegalStateException("Invalid key/value pair: " + param)
      (segments(0), segments(1))
    }

    val segments = cmd.split('(')

    if ( segments.length != 2 )
      throw new IllegalStateException("Invalid command: " + cmd)

    val params = segments(1).dropRight(1).split(',')
    val keyValuePairs = params.map(splitParam).toMap

    (segments(0), keyValuePairs)
  }
}
