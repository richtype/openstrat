/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile scale of 320km. */
object Terr320E150 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e150(120)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, sice),
      TRow(164, WSeaIce),
      TRow(162, WSeaIce * 2),
      TRow(160, WSeaIce * 2),
      TRow(158, Hland(3, 5, tundra, WSeaIce), WSeaIce * 2),
      TRow(156, tundra * 2, Hland(2, 0, tundra, WSeaIce)),
      TRow(154, tundra * 4),
      TRow(152, tundra, taiga * 3),
      TRow(150, taiga * 4),
      TRow(148, taiga * 3, sea, taiga),
      TRow(146, sea * 3, taiga, sea),
      TRow(144, sea * 3, Hland(2, 4, taiga), Hland(2, 1, taiga)),
      TRow(142, taiga * 2, sea * 2, Hland(3, 2, taiga), sea),
      TRow(140, taiga, sea * 5),
      TRow(138, Hland(2, 2, taiga), sea * 6),
      TRow(136, sea, Hland(3, 5, hillyForest), sea * 5),
      TRow(134, Hland(3, 3, hillyForest), Hland(2, 2, hillyForest), sea * 5),
      TRow(132, sea, Hland(2, 0, hilly), sea * 5),
      VRow(131, Mouth(5626, HVDn)),
      TRow(130, hilly, Hland(1, 2, hilly), sea * 6),
      TRow(128, Hland(2, 2, hilly), sea * 7),
    )
  }
  help.run
}
