
package org.sboles.scalatron

object Master extends BotTrait {

  override def react(params: Map[String, String]): String = {
    val view = View(params("view"))
    val name = params("name")

    view.offsetToNearest('B') match {
      // eat Fluppet
      case Some(offset) => reactToFluppet(offset, view, params)
      case None => view.offsetToNearest('P') match {
        // chase and eat Zugar
        case Some(offset) => reactToZugar(offset, view, params)
        case None => view.offsetToNearest('b') match {
          // avoid Snorg
          case Some(offset) => reactToSnorg(offset, view, params)
          case None => view.offsetToNearest('s') match {
            // chase Enemy Slave
            case Some(offset) => reactToEnemySlave(offset, view, params)
            case None => search(view, params)
          }
        }
      }
    }
  }

  /*
  override def search(view: View, params: Map[String, String]): String = {
    val energy = params("energy")

    val last = lastMove(params)

    // move(XY(0,0))

    view.offsetToNearest('W') match {
      // avoid Wall
      case Some(offset) => "Move(direction=" + (
        if ( view.center.distanceTo(offset.signum) <= 1.0 ) offset.signum match {
          case XY.Right => XY.Left
          case XY.Left => XY.Right
          case XY.Up => XY.Down
          case XY.Down => XY.Up
          case _ => XY.Right
        } else XY.Right ) + ")"
      case None => "Move(direction=" + XY.Right + ")"
    }
  }
  */

  def reactToZugar(zugar: XY, view: View, params: Map[String, String]): String = {
    println("React to Zugar at " + zugar)
    chase(zugar)
  }

  def reactToFluppet(fluppet: XY, view: View, params: Map[String, String]): String = {
    println("React to Fluppet at " + fluppet)

    view.offsetToNearest('P') match {
      case Some(zugar) => {
        val fluppetDistance = view.center.distanceTo(fluppet)
        val zugarDistance = view.center.distanceTo(zugar)
        println("- fluppet distance: " + fluppetDistance)
        println("- zugar distance: " + zugarDistance)
        if ( zugarDistance < fluppetDistance ) {
          println("- chase zugar instead")
          reactToZugar(zugar, view, params)
        } else {
          println("- continue chasing fluppet")
          chase(fluppet)
        }
      }
      case None => {
        chase(fluppet)
      }
    }
  }

  def reactToSnorg(snorg: XY, view: View, params: Map[String, String]): String = {
    avoid(snorg)
  }

  def reactToToxifera(toxifera: XY, view: View, params: Map[String, String]): String = {
    avoid(toxifera)
  }

  def reactToEnemySlave(slave: XY, view: View, params: Map[String, String]): String = {
    chase(slave)
  }
}
