
package org.sboles.scalatron

trait BotTrait {

  def move(xy: XY) = {
    println("Move: " + xy)
    "Move(direction=" + xy + ",last=" + xy + ")"
  }

  def spawn(offset: XY, energy: Int) = {
    println("Spawn: " + offset)
    "Spawn(direction=" + offset.signum + ",name=slave,energy=" + energy + ")"
  }

  def spawn(offset: XY): String = spawn(offset, 100)

  def chase(offset: XY) = move(offset.signum)

  def avoid(offset: XY) = move(offset.signum.negate)

  def search(view: View, params: Map[String, String]): String

  def react(params: Map[String, String]): String
}
