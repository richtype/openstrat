/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait BoundedElem extends GeomElem
{ /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: BoundingRect
  def boundingWidth: Double = boundingRect.width
  def boundingHeight: Double = boundingRect.height
}

trait AlignedElem extends BoundedElem
{
  def xTopLeft: Double
  def yTopLeft: Double
  def topLeft: Vec2
  def xTopRight: Double
  def yTopRight: Double
  def topRight: Vec2
  def xBottomRight: Double
  def yBottomRight: Double
  def bottomRight: Vec2
  def xBottomLeft: Double
  def yBottomLeft: Double
  def bottomLeft: Vec2
}