/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import ostrat.pEarth._, prid.phex._

/** Hex tile grids for Earth with a hex scale of 320km, a C scale of 80km. */
package object eg320
{
  val fullTerrs: RArr[Long320Terrs] = RArr(Terr320E0, Terr320E30, Terr320E60, Terr320E90, Terr320E120, Terr320E150,Terr320E180,
    Terr320W150, Terr320W120, Terr320W90, Terr320W60, Terr320W30)

  def fullTerrsSubHCenLayer(implicit subSys: EGrid320LongMulti): HCenLayer[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    subSys.grids(i).newHCenSubLayer(ft.grid, ft.terrs)
  }.combine

  def fullTerrsSubSideLayer(implicit subGrid: EGrid320LongMulti): HSideBoolLayer =
  {
    val arr = iToMap(0, subGrid.numGrids - 1) { i =>
      val ft = fullTerrs((i + subGrid.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subGrid.sideBoolsFromPairs(arr)
  }
}