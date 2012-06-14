
package org.sboles.scalatron

object Slave extends BotTrait{

  override def react(params: Map[String, String]): String = {
    val view = View(params("view"))
    val name = params("name")
    val energy = params("energy")

    view.offsetToNearest('s') match {
      // Attack Enemy Slave
      case Some(offset) => chase(offset)
      case None => view.offsetToNearest('m') match {
        // Attack Enemy Master
        case Some(offset) => chase(offset)
        case None => view.offsetToNearest('W') match {
          // Navigate Walls
          case Some(offset) => avoid(offset)
          case None => "Move(direction=" + XY.Left + ")"
        }
      }
    }
  }

  override def search(view: View, params: Map[String, String]): String = {
    val energy = params("energy")

    move(XY(0,0))
  }
}
