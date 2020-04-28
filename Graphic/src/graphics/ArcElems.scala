/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv._, Colour.Black

/** Circular Arc Draw PaintElem. */
final case class CArcDraw(arc: CArc, lineWidth: Double = 2.0, colour: Colour = Black) extends PaintElem with TransSimerUser
{ type RigidT = CArcDraw
  type MemT = CArc
  override def geomMem: CArc = arc
  override def newThis(transer: CArc): CArcDraw = CArcDraw(transer, lineWidth, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.cArcDraw(this)
  def startText = TextGraphic("st", 10, arc.pStart, colour)
  def clkStr: String = ife(arc.clock, "clk", "anti")
  def cenText: TextGraphic = TextGraphic(clkStr, 10, arc.pCen, colour)
  def endText: TextGraphic = TextGraphic("end", 10, arc.pEnd, colour)
  def cenEndTexts = Arr(cenText, endText)
  def startCenEndTexts = Arr(startText, cenText, endText)
}