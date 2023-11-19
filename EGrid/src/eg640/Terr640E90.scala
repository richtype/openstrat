/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 75° east to 105° east, centred on 90° east. Hex tile scale 640km.  */
object Terr640E90 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e90(112)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(130, tundra),
      TRow(128, hillyTundra),
      TRow(126, taiga, Land(Mountains, Taiga, LandFree)),
      TRow(124, taiga * 2),
      TRow(122, forest, mtain),
      TRow(120, savannah, sahel, sahel),
      TRow(118, sahel, hillyDesert, hillyDesert),
      TRow(116, desert, desert, hillyDesert),
      TRow(114, Land(Mountains, Desert, LandFree), hillyDesert, hillyDesert, mtain),
      TRow(112, mtain, Land(Mountains, Desert, LandFree), mtain, hills),
    )
  }
  help.run
}