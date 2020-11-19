/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Base trait for [[Angle]], [[AngleVec]], [[Latitude]] and [[Longitude]]. */
trait AngleLike extends Any
{ /** The value of this angle expressed in degrees. */
  @inline def degs: Double = secs / SecsInDeg

  /** The value of this angle expressed in arc seconds of a degree. */
  @inline final def secs: Double = milliSecs / 1000

  /** The angle expressed in thousandths of an arc second of a degree. */
  def milliSecs: Double

  /** The value of the angle expressed in radians. */
  @inline final def radians: Double = milliSecs.milliSecsToRadians

  /** The sine value of this angle. */
  @inline def sin: Double = math.sin(radians)

  /** The cosine value of this angle. */
  @inline def cos: Double = math.cos(radians)
}