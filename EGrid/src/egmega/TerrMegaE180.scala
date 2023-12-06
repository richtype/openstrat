/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 175° east to 175° west, centred on 180° east. Hex tile scale 1 megametre or 1000km. */
object TerrMegaE180 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e180(82)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(117, Mouth(6658, HVUp)),
      TRow(116, Cape(2, 1, hillyTundra)),
      VRow(115, Mouth(6654, HVUL),  BendIn(6656, HVUp)),
      TRow(114, SideB(), sea),
      TRow(90, Isle(hilly)),
    )
  }
  help.run
}