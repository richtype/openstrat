/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° east to 75° east, centred on 60° east. Hex tile scale of 320km. */
object Terr320E60 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e60(118)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, SeaIcePerm),
      TRow(164, SeaIceWinter),
      TRow(162, SeaIceWinter * 2),
      TRow(160, tundra, SeaIceWinter),
      TRow(158, hillyTundra, SeaIceWinter, tundra),
      TRow(156, SeaIceWinter * 2, tundra),
      VRow(155, SetSep(2553, SeaIceWinter)),
      TRow(154, tundra * 4),
      VRow(153, BendIn(2552, HVUL, 9, SeaIceWinter)),
      TRow(152, taiga * 4),
      TRow(150, taiga * 4),
      TRow(148, forest, taiga * 4),
      TRow(146, forest * 2, taiga * 3),
      TRow(144, forest * 5),
      TRow(142, level * 6),
      TRow(140, level * 2, desert * 3, level),
      TRow(138, level, desert * 6),
      VRow(137, MouthOld(2552, HVUR, 3, Lake)),
      TRow(136, CapeOld(2, 1, level, Lake), CapeOld(5, 1, desert, Lake), desert * 5),
      TRow(134, Lake, desert * 5, mtainOld),
      TRow(132, mtainOld, Lake, desert * 3, mtainOld * 2),
      TRow(130, mtainOld, CapeOld(1, 1, mtainOld, Lake), CapeOld(5, 1, desert, Lake), desert * 3, mtainOld * 2),
      VRow(129, MouthOld(2552, HVDn, 3, Lake)),
      TRow(128, hillyDesert, desert * 5, mtainOld * 2),
      TRow(126, desert, mtainOld, desert * 5, level),
      TRow(124, desert, level, mtainOld, desert * 4, level * 2),
      VRow(123, MouthOld(2546, HVUL)),
      TRow(122, CapeOld(1, 1, desert), CapeOld(3, 2, hillyDesert), CapeOld(3, 1, mtainOld), desert * 3, level, desert * 2),
      TRow(120, desert * 2, sea, CapeOld(0, 2, hillyDesert), sea * 2, hillyDesert, level, desert),
      TRow(118, desert * 4, sea * 3, level * 2),
    )
  }

  help.run
}