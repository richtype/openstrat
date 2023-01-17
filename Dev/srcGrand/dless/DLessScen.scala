/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import prid._, phex._, egrid._, eg320._, pEarth._

trait DLessScen extends HSysTurnScen
{ def title: String = "DLessScen"
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptLayer[WSide]
  val sTerrsDepr: HSideBoolLayer
  val offsets: HCornerLayer
}

object DLessScen1 extends DLessScen
{
  override def turn: Int = 0

  override implicit val gridSys: EGrid320LongMulti = new EGrid320LongMulti { ThisSys =>
    override val grids: RArr[EGridLongFull] = EGrid320.grids(2, 0, 124)

    override def headGridInt: Int = 0

    override def gridsXSpacing: Double = 40

    override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))

    override def adjTilesOfTile(tile: HCen): HCenArr = ???
  }

  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
  override val sTerrsDepr: HSideBoolLayer = fullTerrsSideBoolLayerSpawn
  override val offsets: HCornerLayer = gridSys.newHVertOffsetLayer
}

object DLessScen2 extends DLessScen
{
  deb("Scen 2")
  override def turn: Int = 0

  override implicit val gridSys: EGrid320Long = BritReg.britGrid

  override val terrs: HCenLayer[WTile] = BritReg.britTerrs
  override val sTerrs: HSideOptLayer[WSide] = BritReg.britSTerrs
  override val sTerrsDepr: HSideBoolLayer = BritReg.britSTerrsDepr
  override val offsets: HCornerLayer = gridSys.newHVertOffsetLayer
}