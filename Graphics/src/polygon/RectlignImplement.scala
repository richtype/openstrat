/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** Implementation class for Rectanglelign, a rectangle aligned to the X and Y axes. */
final case class RectlignImplement(width: Double, height: Double, xCen: Double, yCen: Double) extends Rectlign
{ type ThisT = RectlignImplement
  override def fTrans(f: Vec2 => Vec2): RectlignImplement = ???
  override def shapeAttribs: Arr[XANumeric] = ???
  override def rotateRadians(radians: Double): RectImplement = ???
  override def reflectX: ThisT = fTrans(_.reflectX)
  override def reflectY: ThisT = fTrans(_.reflectY)
  def reflectXOffset(yOffset: Double): ThisT = fTrans(_.reflectXOffset(yOffset))
  def reflectYOffset(xOffset: Double): ThisT = fTrans(_.reflectYOffset(xOffset))
  //override def reflect(line: Line): Polygon = ???
  //override def reflect(line: Sline): Polygon = ???

  override def xyScale(xOperand: Double, yOperand: Double): Polygon = ???

  override def fillOld(fillColour: Colour): ShapeFillOld = ???

  override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???

  override def fillDrawOld(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???
}

/** Companion object for the Rectlign class */
object RectlignImplement
{ def apply(cen: Vec2, width: Double, height: Double): RectlignImplement = new RectlignImplement(width, height, cen.x, cen.y)
}