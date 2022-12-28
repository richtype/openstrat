/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr160W30 extends LongTerrs
{
  override implicit val grid: EGrid160LongFull = EGrid160.w30(276)

  override val terrs: HCenLayer[WTile] =
  {
    val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, cStart, tileValues :_*); () }
    gs(320, 11768, ice * 4, sea)
    gs(318, 11770, ice * 4, sea)
    gs(316, 11768, ice * 4, sea * 2)
    gs(314, 11766, ice * 5, sea)
    gs(312, 11764, ice * 5, sea * 2)
    gs(310, 11766, ice * 4, sea * 3)
    gs(308, 11764, ice * 3, sea * 5)
    gs(306, 11762, ice * 2, sea * 4, hills * 2)
    gs(304, 11764, ice, sea * 4, tundra * 3)
    gs(302, 11762, ice, sea * 5, tundra * 3)
    gs(300, 11760, tundra, sea * 8)
    gs(298, 11758, tundra, sea * 9)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    //res.setTruesInts((142, 508), (143, 507), (144, 522), (145, 521))
    res
  }

  //def regGrid: HGridReg = HGridReg(138, 148, 504, 520)

  def regTerrs: HCenLayer[WTile] = EGrid160.britain.newHCenSubLayer(EGrid160.e0(138), terrs)

  def regScen: EScenBasic = new EScenBasic {
    override implicit val gridSys: EGrid160LongPart = EGrid160.britain// regGrid
    override val terrs: HCenLayer[WTile] = regTerrs
    override val sTerrs: HSideBoolLayer = gridSys.newSideBools
    sTerrs.setTruesInts((142, 508), (143, 507))(gridSys)
  }
}