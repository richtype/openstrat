/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.  */
object Terr640E150 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e150(112)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, tundra),
      TRow(126, mtain, hillyTaiga),
      TRow(124, Cape.a(3, 1, hillyTaiga), hillyTaiga),
      VRow(123, Mouth(5630, HVUL), Mouth(5634, HVUR)),
      TRow(122, sea, Cape.a(2, 4, hillyTaiga)),
      VRow(121, Mouth(5630, HVUp)),
      TRow(120, Cape.a(2, 1, taiga), sea * 2),
      TRow(118, Isle(hilly), sea * 2),
      TRow(116, Cape.a(5, 4, hilly), sea * 2),
      TRow(114, Cape.a(2, 2, hilly), sea * 3),
    )
  }
  help.run
}