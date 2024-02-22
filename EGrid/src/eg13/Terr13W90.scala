/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75° east to 105° east, centred on 90° east. Hex tile scale 1300km. Baffin Island appears at this scale by Cuba is too
 *  small. */
object Terr13W90 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w90(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, Isle10(tundra)),
      TRow(112, taiga),
      VRow(111, MouthLt(9730, HVDn, 7)),
      TRow(110, forest),
      TRow(108, land),
      TRow(106, savannah, Cape(2, 1)),
      VRow(105, MouthOld(9726, HVDL)),
      TRow(104, Cape(0, 2, hillyJungle), sea),
      VRow(103, SetSep(9725), BendOut(9730, HVUR), BendOut(9732, HVUp)),
      TRow(102, sea, hillyJungle),
      VRow(101),
      TRow(100, sea, hillyJungle),
      VRow(99, BendIn(9728, HVDR, 13), BendOut(9730, HVUL, 6)),
      TRow(98, sea, mtain),
      VRow(97, BendIn(9728, HVUR, 13), MouthRt(9730, HVDR, 7)),
      VRow(91, BendIn(9728, HVDR, 13), MouthRt(9730, HVUR)),
      TRow(90, mtain),
      VRow(89, BendIn(9728, HVUR, 13), MouthRt(9730, HVDR, 7)),
      VRow(87, MouthOld(9728, HVDR, 3, wice)),
      TRow(86, ice)
    )
  }
  help.run

  {
    hexNames.setRow(108, "US North West")
    hexNames.setRow(106, "US South Central", "US South East" )
  }
}