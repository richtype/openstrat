/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

object CircleIcon

final case class Circle(radius: Double, xCen: Double, yCen: Double) extends GeomElemNew
{ /** This is wong. */
  override def fTrans(f: Vec2 => Vec2): GeomElemNew = { deb("This is wrong."); Circle(radius, f(cen)) }
  def cen: Vec2 = xCen vv yCen

  override def slate(offset: Vec2): Circle = Circle(radius, cen + offset)

  /** Translate geometric transformation. */
  @inline def slate(xOffset: Double, yOffset: Double): Circle = Circle(radius, xCen + xOffset, yCen + yOffset)

  override def scale(operand: Double): Circle = Circle(radius * operand, cen * operand)

  override def mirrorXOffset(yOffset: Double): Circle = Circle(radius, cen.mirrorXOffset(yOffset))

  override def mirrorX: Circle = Circle(radius, xCen, -yCen)

  override def mirrorYOffset(xOffset: Double): Circle = Circle(radius, cen.mirrorYOffset(xOffset))

  override def mirrorY: Circle = Circle(radius, -xCen, yCen)

  override def prolign(matrix: ProlignMatrix): Circle = Circle(radius * matrix.vFactor, cen.prolignTrans(matrix))
}

/** This object provides factory methods for circles. */
object Circle
{ def apply(radius: Double, cen: Vec2 = Vec2Z): Circle = new Circle(radius, cen.x, cen.y)
  implicit val slateImplicit: Slate[Circle] = (circle, offset) => circle.slate(offset)
}

final case class CircleOld(radius: Double, x: Double, y: Double) extends EllipseLike
{ //override type AlignT = Circle
  override def fTrans(f: Vec2 => Vec2): EllipseLike = ???
  def vCen: Vec2 = x vv y
  override def shear(xScale: Double, yScale: Double): Ellipse = new Ellipse(x, y, x + radius, 0, radius)
  override def rotateRadians(radians: Double): CircleOld = CircleOld(radius, vCen.rotateRadians(radians))
  override def slateOld(offset: Vec2): CircleOld = CircleOld(radius, x + offset.x, y + offset.y)
  override def scaleOld(operand: Double): CircleOld = CircleOld(radius * operand, x * operand, y * operand)

  override def mirror(line: Line2): CircleOld = CircleOld(radius, vCen.mirror(line))

  def fill(colour: Colour): CircleFillOld = CircleFillOld(this, colour)
  def draw(lineWidth: Double = 2, colour: Colour): CircleDraw = CircleDraw(this, lineWidth, colour)
  def fillDraw(fillColour: Colour, lineWidth: Double = 2, lineColour: Colour): CircleFillDraw =
    CircleFillDraw(this, fillColour, lineWidth, lineColour)
}

/** This object provides factory methods for circles. */
object CircleOld
{
  def apply(radius: Double, cen: Vec2 =Vec2Z): CircleOld = new CircleOld(radius, cen.x, cen.y)

  def segs(scale: Double = 1.0): PolyCurve =
  { val a = ArcSeg(Vec2Z, Vec2(0.5 * scale, 0))
    val sg1 = (1 to 4).map(i => (a.rotate(Angle(- math.Pi / 2 * i))))
    PolyCurve(sg1 :_*)
  }

  def fillNew(colour: Colour): Unit = ???

  def fill(radius: Double, colour: Colour, posn: Vec2 = Vec2Z): PolyCurveFill =
  { val fSegs = segs(radius).slateOld(posn)
    PolyCurveFill(fSegs, colour)
  }
}