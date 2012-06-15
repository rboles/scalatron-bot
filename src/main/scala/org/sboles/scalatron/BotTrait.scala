
package org.sboles.scalatron

/**
 * @author sboles
 */
trait BotTrait {

  def move(xy: XY) = {
    println("Move: " + xy)
    "Move(direction=" + xy + ",last=" + xy + ")"
  }

  def move(xy: XY, view: View): String = {
    println("Cell in from of me: '" + view.cellAtAbsPos(xy) + "'")

    view.cellAtAbsPos(xy) match {
      case '_' => move(xy.negate)
      case _ => move(xy)
    }
  }

  def spawn(offset: XY, energy: Int) = {
    println("Spawn: " + offset)
    "Spawn(direction=" + offset.signum + ",name=slave,energy=" + energy + ")"
  }

  def spawn(offset: XY): String = spawn(offset, 100)

  def chase(offset: XY) = move(offset.signum)

  def avoid(offset: XY) = move(offset.signum.negate)

  def search(view: View, params: Map[String, String]): String = {
    val last = lastMove(params)

    println("Direction of last move: " + last)

    move(last, view)
  }

  def defaultDirection = XY(0,1)

  def lastMove(params: Map[String, String]): XY =
    params.contains("last") match {
      case true => XY(params("last"))
      case false => defaultDirection
    }

  def react(params: Map[String, String]): String = move(XY.Zero)
}
