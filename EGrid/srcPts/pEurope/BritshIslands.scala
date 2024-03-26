/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] Graphical representation of the island of Ireland. Depends on nothing. */
object IrelandNorth extends EArea2("Ireland north", 53.36 ll -7.63, oceanic)
{ val north: LatLong = 55.38 ll -7.37
  val torHead: LatLong = 55.19 ll -6.06
  val skernaghanPoint: LatLong = 54.859 ll -5.762
  val laganMouth: LatLong = 54.637 ll -5.874
  val greyPoint: LatLong = 54.677 ll -5.740
  val nIrelandE: LatLong = 54.48 ll -5.43
  val stJohnsPoint: LatLong = 54.23 ll -5.66
  val dundalk: LatLong = 54.01 ll -6.34
  val p25: LatLong = 53.56 ll -6.08
  val wicklowHead: LatLong = 52.97 ll -6.00
  val southEast: LatLong = 52.17 ll -6.36
  val harryLock: LatLong = 51.99 ll -7.59
  val baltimore: LatLong = 51.47 ll -9.37
  val dunquin: LatLong = 52.11 ll -10.45
  val loopHead: LatLong = 52.56 ll -9.94
  val p65: LatLong = 52.93 ll -9.47
  val rockIsland: LatLong = 53.15 ll -9.86
  val p70: LatLong = 53.41 ll -10.18
  val ardoone: LatLong = 54.30 ll -10.00
  val derkmorePoint: LatLong = 54.27 ll -8.65
  val malinBeg: LatLong = 54.66 ll -8.79
  val p95: LatLong = 55.16 ll -8.28

  override val polygonLL = PolygonLL(north, torHead, skernaghanPoint, laganMouth, greyPoint, nIrelandE, stJohnsPoint, dundalk, p25, wicklowHead,
    southEast, harryLock, baltimore, dunquin, loopHead, p65, rockIsland, p70, ardoone, derkmorePoint, malinBeg, p95)
}

/** [[polygonLL]] Graphical representation of the island of Ireland. Depends on nothing. */
object IrelandSouth extends EArea2("Irelandsouth ", 53.0 ll -7.63, hillyOce)
{
  override val polygonLL = PolygonLL()
}