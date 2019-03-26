/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import geom._, pCanv._, pGrid._

/** Main application for Unus Game. */
class UnusGui(canv: CanvasPlatform, grid: UnusGrid)
{
  new UnusSetGui(canv, grid)
}

class UnusSetGui(val canv: CanvasPlatform, val grid: UnusGrid) extends TileGridGui[UTile, SideBare, UnusGrid]("Unus Game")
{
  //Required members
  var pScale: Double = scaleAlignMin
  var focus: Vec2 = grid.cen
  def lines = grid.sideLinesAll.lMap(l => l.fTrans(fTrans).draw(2, Colour.Red))
  override def mapObjs = tilesCoodFlatMapListAll{c =>
    val col = tileDestinguishColour(c)
    val text = TextGraphic(c.xyStr, 12, coodToDisp(c), col.contrastBW)    
    List(tileFill(c, col), text)    
  } //++ lines
  
  //optional members
  mapPanel.backColour = Colour.Wheat
  eTop()
  repaintMap
}