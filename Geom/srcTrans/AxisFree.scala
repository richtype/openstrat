/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A convenience trait for geometric elements that should only be mixed into final classes, [[GeomElem]]s, that don't need special axes method implementations.
 *  You will need to set ThisT and implement / reimplement rotate and reflect methods to correct type. */
trait AxisFree extends GeomElem
{ type ThisT <: AxisFree
  override def rotate(angle: AngleVec): ThisT
  override def rotate90: ThisT = rotate(DegVec90)
  override def rotate180: ThisT = rotate(DegVec180)
  override def rotate270: ThisT = rotate(DegVec270)
  override def reflect(lineLike: LineLike): ThisT
  override def negX: ThisT = reflect(YAxis)
  override def negY: ThisT = reflect(XAxis)
}
