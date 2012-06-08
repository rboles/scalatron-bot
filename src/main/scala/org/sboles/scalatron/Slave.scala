
package org.sboles.scalatron

object Slave {

  def goGetIt(offset: XY) = "Move(direction=" + offset.signum + ")"

  def avoid(offset: XY) = "Move(direction=" + offset.signum.negate + ")"

  def react(params: Map[String, String]): String = {
    val view = View(params("view"))
    val name = params("name")
    val energy = params("energy")

    view.offsetToNearest('s') match {
      // Attack Enemy Slave
      case Some(offset) => goGetIt(offset)
      case None => view.offsetToNearest('m') match {
        // Attack Enemy Master
        case Some(offset) => goGetIt(offset)
        case None => view.offsetToNearest('W') match {
          // Navigate Walls
          case Some(offset) => avoid(offset)
          case None => "Move(direction=" + XY.Left + ")"
        }
      }
    }
  }
}
