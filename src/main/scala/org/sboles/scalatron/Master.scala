
package org.sboles.scalatron

/**
 *
 * TODO
 * - When chasing Fluppet, determine if Zugar is closer than Fluppet, if so,
 * take a detour to go get it.
 */
object Master {

  def react(params: Map[String, String]): String = {
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

  def move(xy: XY) = {
    println("Move " + xy)
    "Move(direction=" + xy + ",last=" + xy + ")"
  }

  def spawn(offset: XY, energy: Int) =
    "Spawn(direction=" + offset.signum + ",name=slave,energy=" + energy + ")"

  def spawn(offset: XY): String = spawn(offset, 100)

  def chase(offset: XY) = move(offset.signum)

  def avoid(offset: XY) = move(offset.signum.negate)

  def reactToZugar(offset: XY, view: View, params: Map[String, String]): String = {
    println("React to Zugar at " + offset)
    chase(offset)
  }

  def reactToFluppet(fluppet: XY, view: View, params: Map[String, String]): String = {
    println("React to Fluppet at " + fluppet)

    // TODO determine which is closer

    view.offsetToNearest('P') match {
      case Some(zugar) => {
        println("Go get Zugar instead")
        reactToZugar(zugar, view, params)
      }
      case None => {
        chase(fluppet)
      }
    }
  }

  def reactToSnorg(offset: XY, view: View, params: Map[String, String]): String = {
    avoid(offset)
  }

  def reactToToxifera(offset: XY, view: View, params: Map[String, String]): String = {
    avoid(offset)
  }

  def reactToEnemySlave(offset: XY, view: View, params: Map[String, String]): String = {
    chase(offset)
  }

  def search(view: View, params: Map[String, String]): String = {
    val energy = params("energy")

    move(XY(0,0))

    /*
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
    */
  }
}
