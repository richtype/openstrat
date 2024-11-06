/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTiles._

object Terr160W30 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.w30(276, 320)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[RowBase] = RArr(
      TRow(320, ice * 4, sea),
      TRow(318, ice * 4, sea),
      TRow(316, ice * 4, sea * 2),
      TRow(314, ice * 5, sea),
      TRow(312, ice * 5, sea * 2),
      TRow(310, ice * 4, sea * 3),
      TRow(308, ice * 3, sea * 5),
      TRow(306, ice * 2, sea * 4, hillyOce * 2),
      TRow(304, ice, sea * 4, tundra * 3),
      TRow(302, ice, sea * 5, tundra * 3),
      TRow(300, tundra, sea * 8),
      TRow(298, tundra, sea * 9),
    )
  }
  help.run
}