/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, geom._, prid._, phex._, pEarth._, pglobe._, Colour._

/** Experimental Gui. Displays grids on world as well as land mass outlines. */
class ExpWorldGui(val canv: CanvasPlatform, scenIn: EScenBasic, viewIn: HGView, isFlat: Boolean) extends GlobeGui("Grid World")
{
  val scen: EScenBasic = scenIn
  deb(scen.title)
  val eas: RArr[EArea2] = earthAllAreas.flatMap(_.a2Arr)
  implicit val gridSys: EGridSys = scen.gridSys

  var scale: Length = gridSys.cScale / viewIn.pixelsPerC
  def gScale: Double = gridSys.cScale / scale
  def ifGScale(minScale: Double, elems : => GraphicElems): GraphicElems = ife(gScale >= minScale, elems, RArr[GraphicElem]())
  var focus: LatLong = gridSys.hCoordLL(viewIn.hCoord)

  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideOptLayer[WSide] = scen.sTerrs
  val corners: HCornerLayer = scen.corners

  val g0Str: String = gridSys match
  { case hgm: EGridMulti => s"grid0: ${hgm.grids(0).numSides}"
    case _ => "Single grid"
  }

  val sideError = gridSys.numSides - gridSys.numInnerSides - gridSys.numOuterSides
  deb(s"In: ${gridSys.numInnerSides}, out: ${gridSys.numOuterSides}, total: ${gridSys.numSides}, error: $sideError, $g0Str" )

  def frame: RArr[GraphicElem] =
  { val polys: HCenPairArr[Polygon] = proj.hCenPolygons(corners)

    /*def tileFills2: RArr[PolygonFill] = proj.hCensMap { hc =>
      corners.tilePoly(hc).map { hvo => hvo.toPt2(proj.transCoord(_)) }.fill(terrs(hc).colour)
    }*/

    def tileFills: RArr[PolygonFill] = polys.pairMap{ (hc, poly) => poly.fill(terrs(hc)(gridSys).colour) }

    val islands: GraphicElems = terrs.hcOptMap{ (tile, hc) => tile match
      {  case island: Island =>
        { val poly = hc.vertsIn(7).map(hv => hv.toPt2(proj.transCoord))
          Some(poly.fill(island.landColour))
        }
        case _ => None
      }
    }

    def tileActives: RArr[PolygonActive] = polys.pairMap{ (hc, poly) => poly.active(hc) }

    def sides1: GraphicElems = proj.sidesOptMap { (hs: HSide) =>
      val sTerr: Option[WSide] = sTerrs(hs)
      val sTerr2 = sTerr.flatMap{
        case s: WSideLands => Some(s)
        case _ => None
      }
      sTerr2.map { st => corners.sideVerts(hs).project(proj).fill(st.colour) }
    }

    def lines1: GraphicElems = proj.linksOptMap{ hs =>
      val hc1 = hs.tileLt
      val hc2 = hs.tileRt
      val t1 = terrs(hc1)
      val t2 = terrs(hc2)
      if (sTerrs(hs).nonEmpty | t1.colour != t2.colour) None
      else
      { val cs: (HCen, Int, Int) = hs.corners
        val ls1 = corners.sideLineHVAndOffset(cs._1, cs._2, cs._3)
        val ls2 = ls1.map(hva => hva.toPt2(proj.transCoord(_)))
        Some(ls2.draw(t1.contrastBW))
      }
    }

    def lines2: GraphicElems = proj.ifTileScale(50, lines1)

    def outerLines = proj.outerSidesDraw(3, Gold)

    def rcTexts1 = terrs.hcOptFlatMap { (hc, terr) =>
      proj.transOptCoord(hc).map { pt =>
        val strs: StrArr = StrArr(hc.rcStr32).appendOption(proj.hCoordOptStr(hc)) +% hc.strComma
        TextGraphic.lines(strs, 12, pt, terr.contrastBW)
      }
    }

    def rcTexts2: GraphicElems = proj.ifTileScale(82, rcTexts1)

    def ifGlobe(f: HSysProjectionEarth => GraphicElems): GraphicElems = proj match
    { case ep: HSysProjectionEarth => f(ep)
      case _ => RArr()
    }

    def seas: GraphicElems = ifGlobe{ep => RArr(earth2DEllipse(ep.metresPerPixel).fill(LightBlue)) }
    def irrFills: GraphicElems = proj match { case ep: HSysProjectionEarth => ep.irrFills; case _ => RArr() }
    def irrLines: GraphicElems = ifGlobe{ ep => ep.irrLines2 }
    def irrNames: GraphicElems = ifGlobe{ ep => ep.irrNames2 }

    seas ++ irrFills ++ irrNames ++ tileFills ++ islands ++ tileActives ++ sides1 ++ lines2/* +% outerLines&*/ ++ rcTexts2 ++ irrLines
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match {
    case (LeftButton, _, cl) =>
    { selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }

  def repaint(): Unit = mainRepaint(frame)
  def thisTop(): Unit = reTop(proj.buttons)

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  thisTop()
  repaint()
}

object ExpWorldGui
{ def apply(canv: CanvasPlatform, grid: EScenBasic, view: HGView, isFlat: Boolean): ExpWorldGui = new ExpWorldGui(canv,grid, view, isFlat)
}