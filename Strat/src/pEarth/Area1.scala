/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._

/** A large area such as North West Europe. */
abstract class Area1(val symName: String, val cen: LatLong) extends GeographicSymbolKey
{ def neighbs: Refs[Area1] = Refs()
  def a2Arr: Refs[Area2]
  def fill: Boolean = true
  def disp2(eg: EarthGui): GraphicElems = a2Arr.flatMap(_.display(eg, fill))
}
