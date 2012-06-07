
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
      case "React" =>
        react(
          params("generation").toInt,
          View(params("view")),
          params)
      case "Goodbye" => ""
      case _ => ""
    }
  }

  def welcome(name: String, path: String, apocalypse: Int, round: Int) = ""

  def react(generation: Int, view: View, params: Map[String, String]) = {
    if ( generation == 0 ) reactAsMaster(view, params)
    else reactAsSlave(view, params)
  }

  def reactAsMaster(view: View, params: Map[String, String]) = {
    val viewString = params("view")
    val view = View(viewString)

    view.offsetToNearest('P') match {
      // eat Zugar
      case Some(offset) => goGetIt(offset) 
      case None => view.offsetToNearest('B') match {
        // chase Fluppet
        case Some(offset) => goGetIt(offset)
        case None => view.offsetToNearest('m') match {
          // avoid Enemy Master
          case Some(offset) => avoid(offset)
          case None => view.offsetToNearest('s') match {
            // avoid Enemy Slave
            case Some(offset) => avoid(offset)
            case None => view.offsetToNearest('W') match {
              // avoid Wall
              case Some(offset) => avoid(offset)
              case None => "Move(direction=" + XY.Right + ")"
            }
          }
        }
      }
    }
  }

  def goGetIt(offset: XY) = "Move(direction=" + offset.signum + ")"

  def avoid(offset: XY) = "Move(direction=" + offset.signum.negate + ")"

  def reactAsSlave(view: View, params: Map[String, String]) = {
    "Status(text=Slave)"
  }
}

case class View(cells: String) {
  val size = math.sqrt(cells.length).toInt

  val center = XY(size/2, size/2)

  def apply(relPos: XY) = cellAtRelPos(relPos)

  def indexFromAbsPos(absPos: XY) = absPos.x + absPos.y * size
  def absPosFromIndex(index: Int) = XY(index % size, index / size)
  def absPosFromRelPos(relPos: XY) = relPos + center
  def cellAtAbsPos(absPos: XY) = cells.charAt(indexFromAbsPos(absPos))

  def indexFromRelPos(relPos: XY) = indexFromAbsPos(absPosFromRelPos(relPos))
  def relPosFromAbsPos(absPos: XY) = absPos - center
  def relPosFromIndex(index: Int) = relPosFromAbsPos(absPosFromIndex(index))
  def cellAtRelPos(relPos: XY) = cells(indexFromRelPos(relPos))

  /**
   * Look for nearest matching thing
   * @param c Character representing thing
   * @return Coordinates of the thing if found or None
   */
  def offsetToNearest(c: Char): Option[XY] = {
    val relativePositions =
      cells.par
      .view
      .zipWithIndex
      .filter(_._1 == c)
      .map(p => relPosFromIndex(p._2))
    if ( relativePositions.isEmpty ) None
    else Some(relativePositions.minBy(_.length))
  }
}

case class XY(val x: Int, val y: Int) {

  def isNonZero = x != 0 || y!= 0
  def isZero = x == 0 && y == 0
  def isNonNegative = x >= 0 && y >= 0

  def updateX(newX: Int) = XY(newX, y)
  def updateY(newY: Int) = XY(x, newY)

  def addToX(dx: Int) = XY(x+dx, y)
  def addToY(dy: Int) = XY(x, y+dy)

  def +(pos: XY) = XY(x+pos.x, y+pos.y)
  def -(pos: XY) = XY(x-pos.x, y-pos.y)
  def *(factor: Double) = XY((x*factor).intValue, (y*factor).intValue)

  def distanceTo(pos: XY) : Double = (this-pos).length
  def length : Double = math.sqrt(x*x + y*y)

  def signum = XY(x.signum, y.signum)

  def negate  = XY(-x, -y)
  def negateX = XY(-x,  y)
  def negateY = XY( x, -y)

  override def toString = x + ":" + y
}

object XY {
  def apply(s: String): XY = {
    val xy = s.split(':').map(_.toInt)
    XY(xy(0), xy(1))
  }

  def random(rnd: util.Random) = XY(rnd.nextInt(3) - 1, rnd.nextInt(3) - 1)

  val Zero = XY(0,0)
  val One  = XY(1,1)

  val Right      = XY( 1,  0)
  val RightUp    = XY( 1, -1)
  val Up         = XY( 0, -1)
  val UpLeft     = XY(-1, -1)
  val Left       = XY(-1,  0)
  val LeftDown   = XY(-1,  1)
  val Down       = XY( 0,  1)
  val DownRight  = XY( 1,  1)
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
