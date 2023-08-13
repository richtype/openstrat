/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._

/** A second level area of the Earth. */
abstract class EArea2(val name: String, val cen: LatLong, val terr: ATerr = Plain) extends GeographicSymbolKey
{
  override def toString = name.appendCommas(terr.toString)
  def aStrs: StrArr = StrArr(name)
  def textScale: Length = 15000.metres
  def colour = terr.colour

  /** A quasi polygon on the earths surface defined in [[LatLong]]s. */
  def polygonLL: PolygonLL

  def places: LocationLLArr = LocationLLArr()

  def display(eg: EarthGuiOld): GraphicElems =
  {
    eg.polyToGlobedArea(polygonLL) match
    {
      case SomeA(d2s) =>
      {
        val v2s: Polygon = d2s.mapPolygon(eg.trans)
        val cenXY: Pt2 = eg.latLongToXY(cen)
        val vis1: GraphicElems = RArr(v2s.fillActive(terr.colour, this))
        val vis2: GraphicElems = RArr(v2s.draw(2.0, terr.colour.redOrPink))
        val vis3: GraphicElems =
          if (eg.scale < textScale) TextGraphic.lines(aStrs, 10, cenXY, terr.contrast) else RArr()
        (vis1 ++ vis2 ++ vis3)
      }

      case SomeB(curveSegDists) => RArr()
      /*{
        val cenXY: Pt2 = eg.latLongToXY(cen)
        val curveSegs: ShapeGenOld = curveSegDists.map(_.toCurveSeg(eg.trans))
          ShapeGenOld.deprDataGenMap(curveSegDists)(_.toCurveSeg(eg.trans))
        Arr(PolyCurveParentFull.fill(cenXY, curveSegs, this, terr.colour))
      }*/

      case NoOptEither => RArr()
    }
  }

  /** Returns a pair of this [[EArea2]] and the [[PolygonM2]] from the given focus and orientation. The polygonM only has points form the side side of
   *  the earth that faces the focus.  */
  def withPolygonM2(focus: LatLongDirn): (EArea2, PolygonM2) =
  { val p3s0: PolygonM3 = polygonLL.toMetres3
    val p3s1: PolygonM3 = p3s0.fromLatLongFocus(focus)
    val p3s2: PolygonM3 = ife(focus.dirn, p3s1, p3s1.rotateZ180)
    val p3s3: PolygonM2 = p3s2.earthZPosXYModify
    (this, p3s3)
  }
}

object EArea2
{
  def apply(symName: String, cen: LatLong, terr: ATerr, latLongArgs: LatLong*) = new EArea2(symName, cen, terr)
  { val polygonLL = PolygonLL(latLongArgs: _*)
  }

  def apply(symName: String, cen: LatLong, terr: ATerr, polygonIn: PolygonLL) = new EArea2(symName, cen, terr)
  { val polygonLL = polygonIn
  }
}