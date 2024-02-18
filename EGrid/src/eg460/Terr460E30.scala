/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale 460km. A hex tile area of 183250975km².
 *  Isle3 4473.900km² => 8768.845km², includes Crete. */
object Terr460E30 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e30(94)
//  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
//  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
//  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(144, mtain),
      VRow(139, MouthOld(1536, HVDL)),
      TRow(138, hillyTundra, Cape(0, 3, hillyTundra)),
      VRow(137, MouthOld(1534, HVUp), MouthOld(1538, HVDL)),
      TRow(136, Cape(2, 1,hillyTaiga), Land(LandLakes, Taiga, Forest), taiga),
      VRow(135, BendAllOld(1532, HVDR)),
      TRow(134, Land(LandLakes, Taiga, Forest), Land(LandLakes, Taiga, Forest), taiga),//check
      VRow(133, BendIn(1530, HVDR), ThreeUp(1532, 6, 6, 0), BendIn(1534, HVUp, 6), MouthRt(1536, HVUR)),
      TRow(132, land, land, taiga),
      VRow(131),
      TRow(130, land * 4),
      TRow(128, land * 2, hilly, land),
      TRow(126, land, hilly, savannah, land),
      VRow(125, MouthOld(1542, HVUp)),
      TRow(124, Cape(4, 1, mtain), hilly, sea * 2, Cape(4, 1, mtain)),
      VRow(123, BendOut(1528, HVDL), MouthOld(1532, HVUp)),
      TRow(122, hillySavannah, Cape(4, 1, hillySavannah), hillySahel, mtain, mtain),
      VRow(121, MouthOld(1528, HVDn)),
      TRow(120, SepB(), sea, Isle3(hillySavannah), Cape(3, 2, hillySavannah), hillySavannah, desert),
      VRow(119, MouthLt(1526, HVDn, 7),  MouthOld(1528, HVDL), BendOut(1532, HVUp), BendOut(1536, HVUp), BendOut(1540, HVDL)),
      TRow(118, sahel, Cape(0, 1, sahel), Cape(0, 1, sahel), Cape(0, 2, sahel), savannah, desert),
      VRow(117, MouthOld(1534, HVDL, 3, Scarp), MouthOld(1536, HVUR, 3, Scarp), MouthOld(1538, HVUp), MouthOld(1540, HVDn), MouthRt(1542, HVUp), MouthOld(1550, HVUp)),
      TRow(116, desert * 2, sahel, desert, desert, desert),
      VRow(115, BendIn(1538, HVUR), ThreeDown(1540, 9, 5, 1), BendIn(1542, HVUL)),
      TRow(114, desert * 3, sahel, Cape(4, 1, hillySahel), desert),
      VRow(113, BendOut(1542, HVDL)),
      TRow(112, desert * 3, hillyDesert, Cape(4, 1, hillyDesert), desert),
      VRow(111, Bend(1544, HVDL, 5, 7)),
      TRow(110, desert, hillyDesert, desert * 2, hillySahel, hillySahel),
      VRow(109, BendIn(1544, HVUR, 13), BendOut(1546, HVDL, 7)),
      TRow(108, desert * 4, sahel, hillySahel, Cape(3, 2, hillySahel)),
      VRow(107, BendAllOld(1552, HVUp, sea, 7)),
      TRow(106, savannah * 2, sahel, savannah, mtain, hillySahel, Cape(0, 1, hillySahel)),
      TRow(104, savannah * 5, mtain, hillySahel),
      TRow(102, hillyJungle * 3, hillySavannah, hillySahel * 2, savannah),
      VRow(101, BendOut(1550, HVDR), MouthRt(1552, HVUR)),
      TRow(100, jungle, hillyJungle * 3, hillySavannah * 2, savannah),
      VRow(99, MouthOld(1538, HVUL, 3, lake), MouthOld(1540, HVDR, 3, lake), BendIn(1548, HVDR, 7), BendIn(1550, HVUL, 13)),
      TRow(98, hillyJungle * 3, hillySavannah * 3),
      VRow(97, MouthRt(1536, HVUL, 6, lake), BendIn(1538, HVDL, 6, lake), BendOut(1546, HVDR, 7), BendIn(1548, HVUL, 13)),
      TRow(96, hillyJungle * 4, hillySavannah * 2),
      VRow(95, MouthLt(1538, HVDn, 6, lake), MouthOld(1540, HVUp, 3, lake), BendOut(1546, HVUR), BendIn(1548, HVDL, 13)),
      TRow(94, hillyJungle * 6),
      VRow(93)
    )
  }
  help.run
}