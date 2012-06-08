
package org.sboles.scalatron

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
