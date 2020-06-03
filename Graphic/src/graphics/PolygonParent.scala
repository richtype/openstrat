/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** Polygon based Graphic class that constains a number of child Graphic Elements. */
case class PolygonParentFull(cen: Vec2, poly: PolygonClass, pointerId: Any, children: Arr[GraphicFullElem]) extends DisplayParentFull with PolyActiveFull
{ type SimerT = PolygonParentFull
  def fTrans(f: Vec2 => Vec2): PolygonParentFull = new PolygonParentFull(f(cen), poly.fTrans(f), pointerId, children.trans(f))
  override def addElems(newElems: Arr[GraphicFullElem]): PolygonParentFull = new PolygonParentFull(cen, poly, pointerId, children ++ newElems)
  override def mutObj(newObj: Any): PolygonParentFull = new PolygonParentFull(cen, poly, newObj, children)
}

/** Companion object of the PolygonParent case class. */
object PolygonParentFull
{
  def fill(cen: Vec2, poly: PolygonClass, evObj: Any, colour: Colour): PolygonParentFull = new PolygonParentFull(cen, poly, evObj, Arr(poly.fill(colour)))

  /** Not sure if this is double filling the polygon */
  def fillDraw(cen: Vec2, poly: PolygonClass, evObj: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black): PolygonParentFull =
    new PolygonParentFull(cen, poly, evObj, Arr(PolygonFillDraw(poly, fillColour, lineWidth, lineColour)))

  def draw(cen: Vec2, poly: PolygonClass, evObj: Any, lineWidth: Double, lineColour: Colour = Black): PolygonParentFull =
    new PolygonParentFull(cen, poly, evObj, Arr(PolygonDraw(poly, lineWidth, lineColour)))

  def fillText(cen: Vec2, poly: PolygonClass, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4, fontColour: Colour = Colour.Black,
               align: TextAlign = CenAlign): PolygonParentFull =
    new PolygonParentFull(cen, poly, evObj, Arr(poly.fill(fillColour), TextGraphic(str, fontSize, poly.polyCentre, fontColour, align)))

  def fillContrastText(cen: Vec2, poly: PolygonClass, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4): PolygonParentFull =
    fillText(cen, poly, evObj, fillColour, str, fontSize, fillColour.contrast)
}

/** Polygon based Graphic class that constains a number of child Graphic Elements. */
case class PolygonParent(cen: Vec2, poly: PolygonClass, pointerId: Any, children: Arr[GraphicElem]) extends DisplayParent
{ type SimerT = PolygonParent
  override def addElems(newElems: Arr[GraphicElem]): PolygonParent = new PolygonParent(cen, poly, pointerId, children ++ newElems)
  override def mutObj(newObj: Any): PolygonParent = new PolygonParent(cen, poly, newObj, children)
  override def boundingRect: BoundingRect = poly.boundingRect
  def mirrorXOffset(yOffset: Double): PolygonParent =
    PolygonParent(cen.mirrorXOffset(yOffset), poly.mirrorXOffset(yOffset), pointerId, children.mirrorXOffset(yOffset))

  def mirrorYOffset(xOffset: Double): PolygonParent =
    PolygonParent(cen.mirrorYOffset(xOffset), poly.mirrorYOffset(xOffset), pointerId, children.mirrorYOffset(xOffset))

  def rotateRadians(radians: Double): PolygonParent =
    PolygonParent(cen.rotateRadians(radians), poly.rotateRadians(radians), pointerId, children.rotateRadians(radians))

  def slate(offset: Vec2): PolygonParent = PolygonParent(cen + offset, poly.slate(offset), pointerId, children.slate(offset))

  def slate(xOffset: Double, yOffset: Double): PolygonParent = slate(xOffset vv yOffset)

  def scale(operand: Double): PolygonParent = PolygonParent(cen * operand, poly.scale(operand), pointerId, children.scale(operand))

  def shear(xScale: Double, yScale: Double): PolygonParent = ???

  def mirror(line: Line2): PolygonParent = ???

  override def mirrorX: PolygonParent = ???

  override def mirrorY: PolygonParent = ???

  override def prolign(matrix: ProlignMatrix): PolygonParent = ???

  override def rotate90: PolygonParent = ???

  override def rotate180: PolygonParent = ???

  override def rotate270: PolygonParent = ???

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???
}