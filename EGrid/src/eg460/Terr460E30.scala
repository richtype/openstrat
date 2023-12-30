/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale 460km. A hex tile area of 183250975km².
 *  Isle3 4473.900km² => 8768.845km², includes Crete. */
object Terr460E30 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e30(106)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      VRow(139, Mouth(1536, HVDL)),
      TRow(138, hillyTundra, Cape(0, 3, hillyTundra)),
      VRow(137, Mouth(1534, HVUp), Mouth(1538, HVDL)),
      TRow(136, Cape(2, 1,hillyTaiga), Land(WetLand, Taiga, Forest), taiga),
      VRow(135, BendAll(1532, HVDR)),
      TRow(134, Cape(3, WetLand, Taiga, Forest, Sea), Land(WetLand, Taiga, Forest), taiga),//check
      VRow(133, Mouth(1536, HVUR)),
      TRow(132, Cape(5, 2), land, taiga),
      VRow(131, Mouth(1530, HVDn)),
      TRow(130, land * 4),
      TRow(128, land * 2, hilly, land),
      TRow(126, land, hilly, savannah, land),
      VRow(125, Mouth(1542, HVUp)),
      TRow(124, Cape(4, 1, mtain), hilly, sea * 2, Cape(4, 1, mtain)),
      VRow(123, BendOut(1528, HVDL), Mouth(1532, HVUp)),
      TRow(122, hillySavannah, Cape(4, 1, hillySavannah), hillySahel, mtain, mtain),
      VRow(121, Mouth(1528, HVDn)),
      TRow(120, SideB(), sea, Isle3(hillySavannah), Cape(3, 2, hillySavannah), hillySavannah, desert),
      VRow(119, Mouth(1526,HVDn),  Mouth(1528, HVDL), BendOut(1532, HVUp), BendOut(1536, HVUp), BendOut(1540, HVDL)),
      TRow(118, sahel, Cape(0, 1, sahel), Cape(0, 1, sahel), Cape(0, 2, sahel), savannah, desert),
      VRow(117, Mouth(1534, HVDL, Scarp), Mouth(1536, HVUR, Scarp), Mouth(1538, HVUp), Mouth(1540, HVDn), Mouth(1542, HVUp), Mouth(1550, HVUp)),
      TRow(116, desert * 2, sahel, desert, desert, desert),
      VRow(115, BendIn(1538, HVUR), ThreeWay(1540), BendIn(1542, HVUL)),
      TRow(114, desert * 3, sahel, Cape(4, 1, hillySahel), desert),
      VRow(113, BendOut(1542, HVDL)),
      TRow(112, desert * 3, hillyDesert, Cape(4, 1, hillyDesert), desert),
      VRow(111, BendAll(1544, HVDL, sea, 5, 7)),
      TRow(110, desert, hillyDesert, desert * 2, hillySahel, hillySahel),
      VRow(109, BendIn(1544, HVUR, 13), BendOut(1546, HVDL, 7)),
      TRow(108, desert * 4, sahel, hillySahel, Cape(3, 2, hillySahel)),
      VRow(107, BendAll(1552, HVUp, sea, 7)),
      TRow(106, savannah * 2, sahel, savannah, mtain, hillySahel, Cape(0, 1, hillySahel)),
    )
  }
  help.run
}