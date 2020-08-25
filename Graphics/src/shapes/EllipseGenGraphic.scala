/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

case class EllipseGenGraphic(val shape: Ellipse, val facets: Arr[ShapeFacet], val children: Arr[ShapeGraphic]) extends EllipseGraphic
{ override def svgStr: String = ???

  /** Translate geometric transformation. Translates this Ellipse Graphic into a modified EllipseGraphic. */
  override def slate(offset: Vec2): EllipseGenGraphic = EllipseGenGraphic(shape.slate(offset), facets, children.map(_.slate(offset)))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): EllipseGenGraphic =
    EllipseGenGraphic(shape.slate(xOffset, yOffset), facets, children.map(_.slate(xOffset, yOffset)))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): EllipseGenGraphic = ???

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): EllipseGenGraphic = ???

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): EllipseGenGraphic = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: EllipseGenGraphic = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: EllipseGenGraphic = ???

  override def prolign(matrix: ProlignMatrix): EllipseGenGraphic = ???

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: EllipseGenGraphic = ???

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: EllipseGenGraphic = ???

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: EllipseGenGraphic = ???

  override def rotateRadians(radians: Double): EllipseGenGraphic = ???

  override def reflect(line: Line): EllipseGenGraphic = ???

  override def scaleXY(xOperand: Double, yOperand: Double): EllipseGenGraphic = ???

  override def shearX(operand: Double): EllipseGenGraphic = ???

  override def shearY(operand: Double): EllipseGenGraphic = ???

  override def reflect(line: Sline): EllipseGenGraphic = ???
}
