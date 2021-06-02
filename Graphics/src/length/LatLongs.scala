/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A base trait for a sequence of [[LatLong]]s. The final classes are more strongly typed as a [[LinePathLL], a [[PolygonLL]]and [[LatLongs]], for a
 * a general collection of [[LatLong]] points. */
trait LatLongsLike extends Any with Dbl2sArr[LatLong]
{ final override def elemBuilder(d1: Double, d2: Double): LatLong = LatLong.secs(d1, d2)
  final override def fElemStr: LatLong => String = _.str
}

/** Immutable flat efficient Array[Double] based collection class for [[geom.LatLong]]s */
final class LatLongs(val arrayUnsafe: Array[Double]) extends AnyVal with LatLongsLike
{ override type ThisT = LatLongs

  override def unsafeFromArray(array: Array[Double]): LatLongs = new LatLongs(array)

  override def typeStr: String = "PolygonLL"

  def toKMs3: PolygonMs3 = ???
  def toPolygonKms: PolygonKMs = ???
}