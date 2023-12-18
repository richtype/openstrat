/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 135° west to 115° west, centred on 120° wast. Hex tile scale 460km.  */
object Terr460W120 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w120(112, 130)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(130, ice),
//      VRow(129, BendOut(8702, HVUp), Mouth(8704, HVUR)),
//      TRow(128, tundra),
//      TRow(126, taiga * 2),
//      TRow(124, taiga * 2),
      TRow(122, sea, mtain, hillySahel),
      TRow(120, sea * 2, hillySavannah, hillySahel * 2),
      TRow(118, sea * 3, Cape(4, 1, hillySahel), hillyDesert, hillySahel),
      VRow(117, BendOut(8706, HVDL), Mouth(8708, HVUL), BendAll(8710, HVDL)),
      TRow(116, sea * 3, Cape(4, 1, hillySahel), Cape(4, 1, hillySahel), hillySahel),
      VRow(115, BendOut(8708, HVDL), BendOut(8712, HVDL)),
      TRow(114, sea * 4, Cape(4, 1, hillySahel), Cape(4, 1, mtain)),
      VRow(113, Mouth(8710, HVDR), BendOut(8714, HVDL)),
      TRow(112, sea * 5, Cape(4, 1, mtain)),
//      TRow(110, sea * 3, Cape(3, 2, hillyDesert)),
//      VRow(109, Mouth(8712, HVUR)),
//      TRow(108, sea * 3, Cape(3, 3, Land(Mountains, Savannah))),
    )
  }
  help.run
}