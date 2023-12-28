/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 1300km or 1.3 MegaMetres. */
object Terr13E120 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e120(86)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, hillyTundra),
      TRow(112, taiga),
      TRow(110, hillyTaiga),
      TRow(108, hillyForest),
      TRow(106, hilly),
      TRow(104, sea * 2),
      TRow(102, SideB(), sea, Isle8(hillyJungle)),
      VRow(101, BendIn(4604, HVDn), SetSide(4605)),
      TRow(100, SideB(), hillyJungle, Cape(4, 4, hillyJungle)),
      VRow(99, BendIn(4604, HVDR), ThreeWay(4606), BendOut(4612, HVDL)),
      TRow(98, Cape(1, 3, hillyJungle), sea),
      VRow(97, BendIn(4604, HVUR)),
      TRow(96, Cape(5, 2, desert), sahel),
      VRow(95, BendOut(4606, HVUL)),
      TRow(94, Cape(3, 3, savannah), desert),
      VRow(93, Mouth(4608, HVUR)),
      VRow(87, Mouth(4612, HVDL, wice)),
      TRow(86, ice)
    )
  }
  help.run
}